package br.com.jgb.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jgb.api.dto.ClienteDTO;
import br.com.jgb.api.dto.ClienteServicoProdutoResponse;
import br.com.jgb.api.dto.PessoaDTO;
import br.com.jgb.api.dto.ProdutoDTO;
import br.com.jgb.api.dto.ServicoDTO;
import br.com.jgb.api.entity.cliente.Cliente;
import br.com.jgb.api.entity.cliente.ClienteProduto;
import br.com.jgb.api.entity.cliente.ClienteServico;
import br.com.jgb.api.entity.fornecedor.Produto;
import br.com.jgb.api.entity.fornecedor.Servico;
import br.com.jgb.api.exceptions.DataBindingViolationException;
import br.com.jgb.api.repository.ClienteProdutoRepository;
import br.com.jgb.api.repository.ClienteRepository;
import br.com.jgb.api.repository.ClienteServicoRepository;
import br.com.jgb.api.repository.PessoaRepository;
import br.com.jgb.api.repository.ProdutoRepository;
import br.com.jgb.api.repository.ServicoRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ClienteServicoRepository clienteServicoRepository;

	@Autowired
	private ClienteProdutoRepository clienteProdutoRepository;

	@Autowired
	private ServicoRepository servicoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;
	

	public ClienteDTO findById(Long idCliente) {
		var dto = new ClienteDTO();

		var pessoa = this.pessoaRepository.findPessoaId(idCliente);

		BeanUtils.copyProperties(pessoa, dto);

		return dto;
	}

	public ClienteDTO findCliente(Long idPessoa) {
		var response = new ClienteDTO();

		var pessoaEntity = pessoaRepository.findById(idPessoa);
		Cliente cliente = this.repository.findClienteByPessoaId(idPessoa);

		if (cliente != null) {
			Long idCliente = cliente.getId();
			var clienteServicos = clienteServicoRepository.findByIdCliente(idCliente);
			var clienteProdutos = clienteProdutoRepository.findByIdCliente(idCliente);

			if (pessoaEntity.isPresent()) {
				PessoaDTO pessoaDTO = new PessoaDTO();
				BeanUtils.copyProperties(pessoaEntity.get(), pessoaDTO);
				response.setPessoa(pessoaDTO);

				var servicosEntity = servicoRepository.findFilterByIdIn(clienteServicos);
				if (!servicosEntity.isEmpty()) {
					List<ServicoDTO> servicosDTO = new ArrayList<>();
					servicosEntity.forEach(item -> {
						ServicoDTO res = new ServicoDTO();
						BeanUtils.copyProperties(item, res);
						servicosDTO.add(res);
					});
					response.setServicosSelecionados(obterIdServios(servicosEntity));
					response.setServicos(servicosDTO);
				}
				var produtosEntity = produtoRepository.findFilterByIdIn(clienteProdutos);
				if (!produtosEntity.isEmpty()) {
					List<ProdutoDTO> produtosDTO = new ArrayList<>();
					produtosEntity.forEach(item -> {
						ProdutoDTO res = new ProdutoDTO();
						BeanUtils.copyProperties(item, res);
						produtosDTO.add(res);
					});
					response.setProdutosSelecionados(obterIdProdutos(produtosEntity));
					response.setProdutos(produtosDTO);

				}
				response.setCode(200);
				response.setMessage("Sucesso");
				return response;
			}
		}
		return response;
	}

	private List<Long> obterIdServios(List<Servico> list) {
		List<Long> response = new ArrayList<>();

		for (Servico item : list) {
			response.add(item.getId());
		}
		return response;
	}

	private List<Long> obterIdProdutos(List<Produto> list) {
		List<Long> response = new ArrayList<>();

		for (Produto item : list) {
			response.add(item.getId());
		}
		return response;
	}

	public List<ClienteDTO> all() {
		List<ClienteDTO> lista = new ArrayList<>();

		var lis = this.repository.all();

		lis.forEach(item -> {
			ClienteDTO pessoa = new ClienteDTO();
			BeanUtils.copyProperties(item, pessoa);
			lista.add(pessoa);
		});

		return lista;
	}

	@Transactional
	public ClienteDTO criar(ClienteDTO obj) {
		obj.setCode(400);
		Cliente clienteEntity = repository.findByCPF(obj.getCpf());
		
		if (clienteEntity == null) {
			clienteEntity = new Cliente();
			clienteEntity.setDataCriacao(LocalDateTime.now());
			clienteEntity.setIdPessoa(pessoaRepository.findFilterCpf(obj.getCpf()).get(0).getId());
			clienteEntity.setSituacao(Boolean.TRUE);
			clienteEntity = this.repository.save(clienteEntity);
		}

		if (!obj.getServicosSelecionados().isEmpty()) {

			for (Long id : obj.getServicosSelecionados()) {
				var itemBanco = servicoRepository.findById(id);
				if (itemBanco.isPresent()) {
					var clienteServico = new ClienteServico();
					clienteServico.setCliente(clienteEntity);
					clienteServico.setServico(itemBanco.get());
					clienteServicoRepository.save(clienteServico);
				}
			}

		}
		if (!obj.getProdutosSelecionados().isEmpty()) {

			for (Long id : obj.getProdutosSelecionados()) {
				var itemBanco = produtoRepository.findById(id);
				if (itemBanco.isPresent()) {
					var clienteProduto = new ClienteProduto();
					clienteProduto.setCliente(clienteEntity);
					clienteProduto.setProduto(itemBanco.get());
					clienteProdutoRepository.save(clienteProduto);
				}
			}
		}

		BeanUtils.copyProperties(clienteEntity, obj);
		obj.setCode(200);
		obj.setMessage("Operacao realizada com sucesso.");
		return obj;
	}

	@Transactional
	public ClienteDTO update(ClienteDTO obj) {
		obj.setCode(400);
		obj.setMessage("Erro ao realizar operação de edição.");

		if (obj.getProdutosSelecionados() == null && obj.getServicosSelecionados() == null) {
			obj.setCode(400);
			obj.setMessage("Selecione ao menos um produto para poder realizar para contratar.");
		} else {

			Cliente clienteEntity = repository.findByCPF(obj.getCpf());
			clienteEntity.setDataAtualizacao(LocalDateTime.now());
			clienteEntity = this.repository.save(clienteEntity);
			Long idCliente = clienteEntity.getId();
			if (obj.getServicosSelecionados() != null && !obj.getServicosSelecionados().isEmpty()) {
				
				for (Long idServico : obj.getServicosSelecionados()) {
					
					var cadastrados = clienteServicoRepository.findByIdClienteAndIdServico(idCliente, idServico);
					
					var itemBanco = servicoRepository.findById(idServico);
					if (itemBanco.isPresent() && cadastrados.isEmpty()) {
						var clienteServico = new ClienteServico();
						clienteServico.setCliente(clienteEntity);
						clienteServico.setServico(itemBanco.get());
						clienteServicoRepository.save(clienteServico);
					}
				}

			}
			if (obj.getProdutosSelecionados() != null && !obj.getProdutosSelecionados().isEmpty()) {

				for (Long idProduto : obj.getProdutosSelecionados()) {
					var cadastrados = clienteProdutoRepository.findByIdClienteAndIdProduto(idCliente, idProduto);
					
					var itemBanco = produtoRepository.findById(idProduto);
					if (itemBanco.isPresent() && cadastrados.isEmpty()) {
						var clienteProduto = new ClienteProduto();
						clienteProduto.setCliente(clienteEntity);
						clienteProduto.setProduto(itemBanco.get());
						clienteProdutoRepository.save(clienteProduto);
					}
				}
			}

			BeanUtils.copyProperties(clienteEntity, obj);
			obj.setCode(200);
			obj.setMessage("Operacao realizada com sucesso.");
		}
		return obj;
	}

	public void delete(Long id) {
		findById(id);
		try {
			this.repository.deleteById(id);
		} catch (Exception e) {
			throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
		}
	}

	/*
	 * OBTER PRODUTOS E SERVICOS PELO ID DO PRODUTO
	 */
	public ClienteServicoProdutoResponse findClientesByIdServicoAndIdProduto(Long idServico, Long idPoduto) {
		ClienteServicoProdutoResponse response = new ClienteServicoProdutoResponse();

		//obtem os clientes pelo codigo do servico
		List<Long> idsClientesServicos = clienteServicoRepository.findByIdServico(idServico);
		List<Cliente> clientesEntityByIdServico = repository.findFilterByIdIn(idsClientesServicos);
		if (!clientesEntityByIdServico.isEmpty()) {
			List<ClienteDTO> responseClienteServico = new ArrayList<>();
			
			clientesEntityByIdServico.forEach(item -> {
				
				var pessoa = pessoaRepository.findById(item.getIdPessoa());
				
				ClienteDTO res = new ClienteDTO();
				res.setNome(pessoa.get().getNome());
				res.setCpf(pessoa.get().getCpf());
				res.setIdPessoa(pessoa.get().getId());
				res.setId(item.getId());
				res.setSituacao(item.getSituacao());
				responseClienteServico.add(res);
			});
			response.setIdServico(idServico);
			response.setClientesServicos(responseClienteServico);
		}
		
		//obtem os clientes pelo codigo do servico
		var idsClientesProdutos = clienteProdutoRepository.findByIdProduto(idPoduto);
		var clientesEntityByIdProduto = repository.findFilterByIdIn(idsClientesProdutos);
		if (!clientesEntityByIdProduto.isEmpty()) {
			List<ClienteDTO>  responseClientesProduto = new ArrayList<>();
			clientesEntityByIdProduto.forEach(item -> {
				var pessoa = pessoaRepository.findById(item.getIdPessoa());
				ClienteDTO res = new ClienteDTO();
				res.setNome(pessoa.get().getNome());
				res.setCpf(pessoa.get().getCpf());
				res.setIdPessoa(pessoa.get().getId());
				res.setId(item.getId());
				res.setSituacao(item.getSituacao());
				responseClientesProduto.add(res);
			});
			response.setIdProduto(idPoduto);
			response.setClientesProdutos(responseClientesProduto);
		}
 
		return response;
	}
}
