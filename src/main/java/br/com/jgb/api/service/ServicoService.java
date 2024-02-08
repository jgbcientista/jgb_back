package br.com.jgb.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jgb.api.dto.PessoaDTO;
import br.com.jgb.api.dto.ProdutoDTO;
import br.com.jgb.api.dto.ServicoDTO;
import br.com.jgb.api.dto.ServicoVinculadoAndNaoVinculadoDTO;
import br.com.jgb.api.entity.fornecedor.FornecedorServico;
import br.com.jgb.api.entity.fornecedor.Produto;
import br.com.jgb.api.entity.fornecedor.Servico;
import br.com.jgb.api.exceptions.DataBindingViolationException;
import br.com.jgb.api.exceptions.ObjectNotFoundException;
import br.com.jgb.api.filter.ProdutoFilter;
import br.com.jgb.api.repository.ClienteProdutoRepository;
import br.com.jgb.api.repository.ClienteRepository;
import br.com.jgb.api.repository.ClienteServicoRepository;
import br.com.jgb.api.repository.FornecedorProdutoRepository;
import br.com.jgb.api.repository.FornecedorRepository;
import br.com.jgb.api.repository.FornecedorServicoRepository;
import br.com.jgb.api.repository.PessoaRepository;
import br.com.jgb.api.repository.ProdutoRepository;
import br.com.jgb.api.repository.ServicoRepository;
import br.com.jgb.api.util.Util;

@Service
public class ServicoService {

	@Autowired
	private ServicoRepository repository;
	
	@Autowired
	private  FornecedorServicoRepository fornecedorServicoRepository; 

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ClienteServicoRepository clienteServicoRepository;

	@Autowired
	private ClienteProdutoRepository clienteProdutoRepository;

	@Autowired
	private ServicoRepository servicoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@Autowired
	private  FornecedorProdutoRepository fornecedorProdutoRepository;

	public List<ServicoDTO> findFilter(String codigo, String descricao) {
		var filter = new ProdutoFilter();

		if (!Util.isNull(codigo)) {
			filter.setCodigo(Integer.valueOf(codigo));
		}
		filter.setDescricao(descricao);

		List<ServicoDTO> listaDTO = new ArrayList<>();
		List<Servico> listaEntidades = null;

		if (!Util.isNull(filter.getDescricao()) && Util.isNull(codigo)) {
			listaEntidades = this.repository.findFilterByDescricao(filter.getDescricao());
		} else if (!Util.isNull(codigo) && Util.isNull(filter.getDescricao())) {
			listaEntidades = this.repository.findFilterByCodigo(filter.getCodigo());
		} else if (!Util.isNull(filter.getDescricao()) && !Util.isNull(codigo)) {
			listaEntidades = this.repository.findFilterByDescricao(filter.getDescricao());
		} else {
			return findAll();
		}
		listaEntidades.forEach(item -> {
			ServicoDTO response = new ServicoDTO();
			BeanUtils.copyProperties(item, response);
			listaDTO.add(response);
		});

		return listaDTO;
	}

	public ServicoDTO findById(Long id) {
		var dto = new ServicoDTO();

		var pessoa = this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Serviço não encontrado."));

		BeanUtils.copyProperties(pessoa, dto);

		return dto;
	}

	public ServicoVinculadoAndNaoVinculadoDTO obterServicosContratdosNaoContrados(Long idPessoa) {
		ServicoVinculadoAndNaoVinculadoDTO response = new ServicoVinculadoAndNaoVinculadoDTO();

		var pessoaEntity = pessoaRepository.obterPessoaById(idPessoa);
		var clienteEntity = this.clienteRepository.findClienteByPessoaId(idPessoa);
		var fornecedorEntity = this.fornecedorRepository.findFornecedorByPessoaId(idPessoa);

		if (clienteEntity != null) {
			Long idCliente = clienteEntity.getId();
			PessoaDTO pessoaDTO = new PessoaDTO();
			var idsClienteServicos = clienteServicoRepository.findByIdCliente(idCliente);
			BeanUtils.copyProperties(pessoaEntity, pessoaDTO);

			// SERVICOS VINCULADOS
			List<ServicoDTO> servicosContrados = obterServicosContratadosPorIds(idPessoa, idCliente, idsClienteServicos);
			response.setServicosContratados(servicosContrados);
			// SERVICOS NAO VINCULADOS
			List<ServicoDTO> servicosNaoContrados  = obterServicosNaoContratadosPorIds(idPessoa, idCliente, idsClienteServicos, servicosContrados);
			response.setServicosNaoContratados(servicosNaoContrados); 
			// PRODUTOS VINCULADOS
			var idProdutos = clienteProdutoRepository.findByIdCliente(idCliente);
			List<ProdutoDTO> produtosVinculados  = obterProdutosContratadosPorIds(idPessoa, idCliente, idProdutos);
			response.setProdutosContratados(produtosVinculados);
			
			// PRODUTOS NAO VINCULADOS
			var produtosNaoVinculados = produtosNaoVinculados(idPessoa, idCliente, idsClienteServicos, produtosVinculados);
			response.setProdutosNaoContratados(produtosNaoVinculados);
		} else if (fornecedorEntity != null) {
			
			Long idFornecedor = fornecedorEntity.getId();
			PessoaDTO pessoaDTO = new PessoaDTO();
			var idsFornecedorServicos = fornecedorServicoRepository.findByIdFornecedor(idFornecedor);
			BeanUtils.copyProperties(pessoaEntity, pessoaDTO);

			// SERVICOS VINCULADOS
			List<ServicoDTO> servicosContrados = obterServicosContratadosPorIds(idPessoa, idFornecedor, idsFornecedorServicos);
			response.setServicosContratados(servicosContrados);
			// SERVICOS NAO VINCULADOS
			List<ServicoDTO> servicosNaoContrados  = obterServicosNaoContratadosPorIds(idPessoa, idFornecedor, idsFornecedorServicos, servicosContrados);
			response.setServicosNaoContratados(servicosNaoContrados); 
			
			// PRODUTOS VINCULADOS
			var idsProdutos = fornecedorProdutoRepository.findByIdFornecedor(idFornecedor);
			List<ProdutoDTO> produtosVinculados  = obterProdutosContratadosPorIds(idPessoa, idFornecedor, idsProdutos);
			response.setProdutosContratados(produtosVinculados);
			// PRODUTOS NAO CONTRATADOS
			var produtosNaoVinculados = produtosNaoVinculados(idPessoa, idFornecedor, idsFornecedorServicos, produtosVinculados);
			response.setProdutosNaoContratados(produtosNaoVinculados);
		}

		return response;
	}

	private List<ProdutoDTO> produtosNaoVinculados(Long idPessoa, Long idCliente, List<Long> idsClienteProdutos, List<ProdutoDTO> produtosVinculados) {
		List<ProdutoDTO> response = new ArrayList<>();
		List<Produto> produtosNaoContrados = produtoRepository.findNaoContratados(idsClienteProdutos);
		if (!produtosVinculados.isEmpty()) {
			if (!produtosNaoContrados.isEmpty()) {

				produtosNaoContrados.forEach(item -> {
					ProdutoDTO res = new ProdutoDTO();
					BeanUtils.copyProperties(item, res);
					res.setIdPessoa(idPessoa);
					res.setIdCliente(idCliente);
					response.add(res);
				});
			}
		} else {
			produtosNaoContrados = produtoRepository.findAll();
			produtosNaoContrados.forEach(item -> {
				ProdutoDTO res = new ProdutoDTO();
				BeanUtils.copyProperties(item, res);
				res.setIdPessoa(idPessoa);
				res.setIdCliente(idCliente);
				response.add(res);
			});
		}
		return response;
	}
	
	private List<ProdutoDTO> obterProdutosContratadosPorIds(Long idPessoa, Long idCliente, List<Long> clienteProdutos) {
		List<ProdutoDTO> response = new ArrayList<>();
		
		List<Produto> produtosContratados = produtoRepository.findContratados(clienteProdutos);

		if (!produtosContratados.isEmpty()) {

			produtosContratados.forEach(item -> {
				ProdutoDTO res = new ProdutoDTO();
				BeanUtils.copyProperties(item, res);
				res.setIdPessoa(idPessoa);
				res.setIdCliente(idCliente);
				response.add(res);
			});
		}
	 
		 
		return response;
	}

	private List<ServicoDTO> obterServicosNaoContratadosPorIds(Long idPessoa, Long idCliente, List<Long> clienteServicos, List<ServicoDTO> servicosContrados) {
		List<ServicoDTO> response = new ArrayList<>();
		
		List<Servico> servicosNaoContrados = servicoRepository.findNaoContratados(clienteServicos);
		if (!servicosContrados.isEmpty()) {
			if (!servicosNaoContrados.isEmpty()) {

				servicosNaoContrados.forEach(item -> {
					ServicoDTO res = new ServicoDTO();
					BeanUtils.copyProperties(item, res);
					res.setIdPessoa(idPessoa);
					res.setIdCliente(idCliente);
					response.add(res);
				});
			}

		} else {
			// nao tiver contrato nenhum servico
			if (servicosContrados.isEmpty()) {
				
				servicosNaoContrados = servicoRepository.findAll();
				servicosNaoContrados.forEach(item -> {
					ServicoDTO res = new ServicoDTO();
					BeanUtils.copyProperties(item, res);
					res.setIdPessoa(idPessoa);
					res.setIdCliente(idCliente);
					response.add(res);
				});
			}
		}
		return response;
	}

	private List<ServicoDTO> obterServicosContratadosPorIds(Long idPessoa, Long idCliente,
			List<Long> clienteServicos) {
		List<ServicoDTO> responseServicosContratados = new ArrayList<>();
		List<Servico> servicosContrados = servicoRepository.findContratados(clienteServicos);
		if (!servicosContrados.isEmpty()) {
			

			servicosContrados.forEach(item -> {
				ServicoDTO res = new ServicoDTO();
				BeanUtils.copyProperties(item, res);
				res.setIdPessoa(idPessoa);
				res.setIdCliente(idCliente);
				responseServicosContratados.add(res);
			});
		}
		return responseServicosContratados;
	}

	public List<ServicoDTO> findAll() {
		List<ServicoDTO> lista = new ArrayList<>();

		var lis = this.repository.findAll();

		lis.forEach(item -> {
			ServicoDTO pessoa = new ServicoDTO();
			BeanUtils.copyProperties(item, pessoa);
			lista.add(pessoa);
		});

		return lista;
	}

	@Transactional
	public ServicoDTO criar(ServicoDTO dto) {
		dto.setCode(400);
		var entity = new Servico();
		entity.setDescricao(dto.getDescricao());
		entity.setDataCriacao(LocalDateTime.now());
		entity.setSituacao(Boolean.TRUE);
		entity = this.repository.save(entity);
		
		if(dto.getIdsFornecedores() != null && !dto.getIdsFornecedores().isEmpty()) {
			for (Long item : dto.getIdsFornecedores()) {
				Long idForncedor = item;
				FornecedorServico fornecedorServico = new FornecedorServico();
				fornecedorServico.setFornecedor(fornecedorRepository.findByPorId(idForncedor));
				fornecedorServico.setServico(repository.findByServicoId(entity.getId()));
				fornecedorServicoRepository.save(fornecedorServico);
			}
		} else {
			dto.setCode(400);
			dto.setMessage("Erro na operação.");
		}
		
	
		
		BeanUtils.copyProperties(entity, dto);
		dto.setCode(200);
		dto.setMessage("Operacao realizada com sucesso.");
		return dto;
	}

	@Transactional
	public ServicoDTO update(ServicoDTO obj) {
		var newObj = repository.findById(obj.getId());

		if (newObj.isPresent()) {
			var entity = newObj.get();
			entity.setId(obj.getId());
			entity.setDescricao(obj.getDescricao());
			entity.setDataAtualizacao(LocalDateTime.now());
			entity.setSituacao(Boolean.TRUE);
			entity = this.repository.save(entity);
			
			if(obj.getIdsFornecedores() != null && !obj.getIdsFornecedores().isEmpty()) {
				for (Long item : obj.getIdsFornecedores()) {
					Long idForncedor = item;
					FornecedorServico fornecedorServico = new FornecedorServico();
					fornecedorServico.setFornecedor(fornecedorRepository.findByPorId(idForncedor));
					fornecedorServico.setServico(repository.findByServicoId(entity.getId()));
					fornecedorServicoRepository.save(fornecedorServico);
				}
			}
			
			BeanUtils.copyProperties(entity, obj);
			obj.setCode(200);
			obj.setMessage("Operacao realizada com sucesso.");
		} else {
			obj.setCode(400);
			obj.setMessage("Não foi possível realizar a edição.");
		}
		return obj;
	}
	
	public void delete(Long id) {
		Optional<Servico> entity = this.repository.findById(id);
		try {
			if (entity.isPresent()) {
				this.repository.delete(entity.get());
			}
		
		} catch (Exception e) {
			throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
		}
	}

	public ServicoDTO removerAssociacaoComServicoCliente(Long idServico, Long idCliente) {
		ServicoDTO res = new ServicoDTO();
		try {
			var associacao = clienteServicoRepository.obterAssociacaoComServico(idServico, idCliente);
			clienteServicoRepository.delete(associacao);
			res.setCode(200);
			res.setMessage("Sucesso");
		} catch (Exception e) {
			res.setCode(400);
			res.setMessage("Não é possível excluir pois há entidades relacionadas");
			return res;
		}
		return res;
	}
	

	public ServicoDTO removerAssociacaoComServicoFornecedor(Long idServico, Long idPessoa) {
		ServicoDTO res = new ServicoDTO();
		try {
			var fornecedor = fornecedorRepository.findFornecedorByPessoaId(idPessoa);
			var associacao = fornecedorServicoRepository.obterAssociacaoComServico(idServico, fornecedor.getId());
			fornecedorServicoRepository.delete(associacao);
			res.setCode(200);
			res.setMessage("Sucesso");
		} catch (Exception e) {
			res.setCode(400);
			res.setMessage("Não é possível excluir pois há entidades relacionadas");
			return res;
		}
		return res;
	}
}
