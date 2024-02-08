package br.com.jgb.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jgb.api.dto.FornecedorDTO;
import br.com.jgb.api.dto.FornecedorServicoProdutoResponse;
import br.com.jgb.api.dto.PessoaDTO;
import br.com.jgb.api.dto.ProdutoDTO;
import br.com.jgb.api.dto.ServicoDTO;
import br.com.jgb.api.entity.fornecedor.Fornecedor;
import br.com.jgb.api.entity.fornecedor.FornecedorProduto;
import br.com.jgb.api.entity.fornecedor.FornecedorServico;
import br.com.jgb.api.entity.fornecedor.Produto;
import br.com.jgb.api.entity.fornecedor.Servico;
import br.com.jgb.api.exceptions.DataBindingViolationException;
import br.com.jgb.api.repository.FornecedorProdutoRepository;
import br.com.jgb.api.repository.FornecedorRepository;
import br.com.jgb.api.repository.FornecedorServicoRepository;
import br.com.jgb.api.repository.PessoaRepository;
import br.com.jgb.api.repository.ProdutoRepository;
import br.com.jgb.api.repository.ServicoRepository;

@Service
public class FornecedorService {

	@Autowired
	private FornecedorRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private FornecedorServicoRepository fornecedorServicoRepository;

	@Autowired
	private FornecedorProdutoRepository fornecedorProdutoRepository;

	@Autowired
	private ServicoRepository servicoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	public FornecedorDTO findById(Long idFornecedor) {
		var dto = new FornecedorDTO();

		var pessoa = this.pessoaRepository.findPessoaId(idFornecedor);

		BeanUtils.copyProperties(pessoa, dto);

		return dto;
	}
	
	
	/*
	 * OBTER PRODUTOS E SERVICOS PELO ID DO FORNECEDOR
	 */
	public FornecedorDTO findServicosProdutosByIdFornecedor(Long idFornecedor) {
		FornecedorDTO response = new FornecedorDTO();

		var fornecedorServicos = fornecedorServicoRepository.findByIdFornecedor(idFornecedor);
		var fornecedorProdutos = fornecedorProdutoRepository.findByIdFornecedor(idFornecedor);
		
		var servicosEntity = servicoRepository.findFilterByIdIn(fornecedorServicos);
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
		var produtosEntity = produtoRepository.findFilterByIdIn(fornecedorProdutos);
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
 
		return response;
	}
	
	public FornecedorDTO findFornecedor(Long idPessoa) {
		var response = new FornecedorDTO();
		response.setCode(400);

		var pessoaEntity = pessoaRepository.findById(idPessoa);
		Fornecedor fornecedor = this.repository.findFornecedorByPessoaId(idPessoa);

		if (fornecedor != null) {
			Long idFornecedor = fornecedor.getId();
			var fornecedorServicos = fornecedorServicoRepository.findByIdFornecedor(idFornecedor);
			var fornecedorProdutos = fornecedorProdutoRepository.findByIdFornecedor(idFornecedor);

			if (pessoaEntity.isPresent()) {
				PessoaDTO pessoaDTO = new PessoaDTO();
				BeanUtils.copyProperties(pessoaEntity.get(), pessoaDTO);
				response.setPessoa(pessoaDTO);

				var servicosEntity = servicoRepository.findFilterByIdIn(fornecedorServicos);
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
				var produtosEntity = produtoRepository.findFilterByIdIn(fornecedorProdutos);
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
	
	public List<FornecedorDTO> all() {
		List<FornecedorDTO> responseLista = new ArrayList<>();

		var lis = this.repository.all();

		lis.forEach(item -> {
			var pessoa = pessoaRepository.findById(item.getIdPessoa());
			
			FornecedorDTO response = new FornecedorDTO();
			response.setCpf(pessoa.get().getCpf());
			response.setNome(pessoa.get().getNome());
			BeanUtils.copyProperties(item, response);
			responseLista.add(response);
		});		

		return responseLista;
	}

	@Transactional
	public FornecedorDTO criar(FornecedorDTO obj) {
		obj.setCode(400);
		Fornecedor fornecedorEntity = repository.findByCPF(obj.getCpf());
		if (fornecedorEntity == null) {
			fornecedorEntity = new Fornecedor();
			fornecedorEntity.setDataCriacao(LocalDateTime.now());
			fornecedorEntity.setIdPessoa(pessoaRepository.findFilterCpf(obj.getCpf()).get(0).getId());
			fornecedorEntity.setSituacao(Boolean.TRUE);
			fornecedorEntity = this.repository.save(fornecedorEntity);
		}

		if (obj.getServicosSelecionados() != null && !obj.getServicosSelecionados().isEmpty()) {

			for (Long id : obj.getServicosSelecionados()) {
				var itemBanco = servicoRepository.findById(id);
				if (itemBanco.isPresent()) {
					var fornecedorServico = new FornecedorServico();
					fornecedorServico.setFornecedor(fornecedorEntity);
					fornecedorServico.setServico(itemBanco.get());
					fornecedorServicoRepository.save(fornecedorServico);
				}
			}

		}
		if (obj.getProdutosSelecionados() != null && !obj.getProdutosSelecionados().isEmpty()) {

			for (Long id : obj.getProdutosSelecionados()) {
				var itemBanco = produtoRepository.findById(id);
				if (itemBanco.isPresent()) {
					var fornecedorProduto = new FornecedorProduto();
					fornecedorProduto.setFornecedor(fornecedorEntity);
					fornecedorProduto.setProduto(itemBanco.get());
					fornecedorProdutoRepository.save(fornecedorProduto);
				}
			}
		}

		BeanUtils.copyProperties(fornecedorEntity, obj);
		obj.setCode(200);
		obj.setMessage("Operacao realizada com sucesso.");
		return obj;
	}

	@Transactional
	public FornecedorDTO update(FornecedorDTO obj) {
		obj.setCode(400);
		obj.setMessage("Erro ao realizar operação de edição.");

		if (obj.getProdutosSelecionados() == null && obj.getServicosSelecionados() == null) {
			obj.setCode(400);
			obj.setMessage("Selecione ao menos um produto para poder realizar para contratar.");
		} else {

			Fornecedor fornecedorEntity = repository.findByCPF(obj.getCpf());
			fornecedorEntity.setDataAtualizacao(LocalDateTime.now());
			fornecedorEntity = this.repository.save(fornecedorEntity);
			Long idFornecedor = fornecedorEntity.getId();
			if (obj.getServicosSelecionados() != null && !obj.getServicosSelecionados().isEmpty()) {
				
				for (Long idServico : obj.getServicosSelecionados()) {
					
					var cadastrados = fornecedorServicoRepository.findByIdFornecedorAndIdServico(idFornecedor, idServico);
					
					var itemBanco = servicoRepository.findById(idServico);
					if (itemBanco.isPresent() && cadastrados.isEmpty()) {
						var fornecedorServico = new FornecedorServico();
						fornecedorServico.setFornecedor(fornecedorEntity);
						fornecedorServico.setServico(itemBanco.get());
						fornecedorServicoRepository.save(fornecedorServico);
					}
				}

			}
			if (obj.getProdutosSelecionados() != null && !obj.getProdutosSelecionados().isEmpty()) {

				for (Long idProduto : obj.getProdutosSelecionados()) {
					var cadastrados = fornecedorProdutoRepository.findByIdFornecedorAndIdProduto(idFornecedor, idProduto);
					
					Produto itemBanco = produtoRepository.findByProdutoId(idProduto);
					if (itemBanco != null && cadastrados.isEmpty()) {
						var fornecedorProduto = new FornecedorProduto();
						fornecedorProduto.setFornecedor(fornecedorEntity);
						fornecedorProduto.setProduto(itemBanco);
						fornecedorProdutoRepository.save(fornecedorProduto);
					}
				}
			}

			BeanUtils.copyProperties(fornecedorEntity, obj);
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
	 * OBTER PRODUTOS E SERVICOS PELO ID DO FORNECEDOR
	 */
	public FornecedorServicoProdutoResponse findFornecedoresByIdServicoAndIdProduto(Long idServico, Long idPoduto) {
		FornecedorServicoProdutoResponse response = new FornecedorServicoProdutoResponse();

		//obtem os fornecedores pelo codigo do servico
		var idsFornecedorServicos = fornecedorServicoRepository.findByIdServico(idServico);
		List<Fornecedor> fornecedoresEntityByIdServico = repository.findFilterByIdIn(idsFornecedorServicos);
		if (!fornecedoresEntityByIdServico.isEmpty()) {
			List<FornecedorDTO> responseFornecedoresServico = new ArrayList<>();
			
			fornecedoresEntityByIdServico.forEach(item -> {
				
				var pessoa = pessoaRepository.findById(item.getIdPessoa());
			 
				FornecedorDTO res = new FornecedorDTO();
				res.setNome(pessoa.get().getNome());
				res.setCpf(pessoa.get().getCpf());
				res.setIdPessoa(pessoa.get().getId());
				res.setId(item.getId());
				res.setSituacao(item.getSituacao());
				responseFornecedoresServico.add(res);
			});
			response.setIdServico(idServico);
			response.setFornecedoresServicos(responseFornecedoresServico);
		}
		
		//obtem os fornecedores pelo codigo do servico
		var idsFornecedorProdutos = fornecedorProdutoRepository.findByIdProduto(idPoduto);
		var fornecedoresEntityByIdProduto = repository.findFilterByIdIn(idsFornecedorProdutos);
		if (!fornecedoresEntityByIdProduto.isEmpty()) {
			List<FornecedorDTO>  responseFornecedoresProduto = new ArrayList<>();
			fornecedoresEntityByIdProduto.forEach(item -> {
				var pessoa = pessoaRepository.findById(item.getIdPessoa());
				FornecedorDTO res = new FornecedorDTO();
				res.setNome(pessoa.get().getNome());
				res.setCpf(pessoa.get().getCpf());
				res.setIdPessoa(pessoa.get().getId());
				res.setId(item.getId());
				res.setSituacao(item.getSituacao());
				responseFornecedoresProduto.add(res);
			});
			response.setIdProduto(idPoduto);
			response.setFornecedoresProdutos(responseFornecedoresProduto);
		}
 
		return response;
	}
	
	public List<FornecedorDTO> findFornecedoresByIdServico(Long idServico) {
		//obtem os fornecedores pelo codigo do servico
		var idsFornecedorServicos = fornecedorServicoRepository.findByIdServico(idServico);
		List<Fornecedor> responseLista = repository.findFilterByIdIn(idsFornecedorServicos);
		List<FornecedorDTO> response = new ArrayList<>();
		
		if (!responseLista.isEmpty()) {
			responseLista.forEach(item -> {
				var pessoa = pessoaRepository.findById(item.getIdPessoa());
				FornecedorDTO res = new FornecedorDTO();
				res.setNome(pessoa.get().getNome());
				res.setCpf(pessoa.get().getCpf());
				res.setIdPessoa(pessoa.get().getId());
				res.setId(item.getId());
				res.setSituacao(item.getSituacao());
				response.add(res);
			});
			
		}
		 
		return response;
	}
	
	public List<FornecedorDTO> findFornecedoresByIdProduto(Long idProduto) {
		//obtem os fornecedores pelo codigo do servico
		var idsFornecedorProdudos = fornecedorProdutoRepository.findByIdProduto(idProduto);
		List<Fornecedor> responseLista = repository.findFilterByIdIn(idsFornecedorProdudos);
		List<FornecedorDTO> response = new ArrayList<>();
		
		if (!responseLista.isEmpty()) {
			responseLista.forEach(item -> {
				var pessoa = pessoaRepository.findById(item.getIdPessoa());
				FornecedorDTO res = new FornecedorDTO();
				res.setNome(pessoa.get().getNome());
				res.setCpf(pessoa.get().getCpf());
				res.setIdPessoa(pessoa.get().getId());
				res.setId(item.getId());
				res.setSituacao(item.getSituacao());
				response.add(res);
			});
			
		}
		 
		return response;
	}
	
	
	public List<FornecedorDTO> findFornecedoresNaoVinculados(Long idServico) {
		//obtem os fornecedores pelo codigo do servico
		List<Fornecedor> responseLista = null;
		
		var idsFornecedorServicos = fornecedorServicoRepository.findByIdServico(idServico);
		
		if(!idsFornecedorServicos.isEmpty()) {
			responseLista = repository.findFilterByIdNotIn(idsFornecedorServicos);
		} else {
			responseLista = repository.all();
		}
		
		List<FornecedorDTO> response = new ArrayList<>();
		if (!responseLista.isEmpty()) {
			responseLista.forEach(item -> {
				var pessoa = pessoaRepository.findById(item.getIdPessoa());
				FornecedorDTO res = new FornecedorDTO();
				res.setNome(pessoa.get().getNome());
				res.setCpf(pessoa.get().getCpf());
				res.setIdPessoa(pessoa.get().getId());
				res.setId(item.getId());
				res.setSituacao(item.getSituacao());
				response.add(res);
			});
			
		}
		 
		return response;
	}
	
	public void deleteFornecedorServico(Long id) {
		findById(id);
		try {
			this.fornecedorServicoRepository.deleteById(id);
		} catch (Exception e) {
			throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
		}
	}
	
	
	
}
