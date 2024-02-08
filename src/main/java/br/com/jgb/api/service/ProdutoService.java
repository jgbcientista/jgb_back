package br.com.jgb.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jgb.api.dto.ProdutoDTO;
import br.com.jgb.api.entity.fornecedor.FornecedorProduto;
import br.com.jgb.api.entity.fornecedor.Produto;
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
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;

	@Autowired
	private FornecedorServicoRepository fornecedorServicoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ClienteServicoRepository clienteServicoRepository;

	@Autowired
	private ServicoRepository servicoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Autowired
	private ClienteProdutoRepository clienteProdutoRepository;

	@Autowired
	private FornecedorProdutoRepository fornecedorProdutoRepository;

	public ProdutoDTO findById(Long id) {
		var dto = new ProdutoDTO();

		var entity = this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Produto contratado não encontrado."));

		BeanUtils.copyProperties(entity, dto);

		return dto;
	}

	public List<ProdutoDTO> findFilter(String codigo, String descricao) {
		var filter = new ProdutoFilter();

		if (!Util.isNull(codigo)) {
			filter.setCodigo(Integer.valueOf(codigo));
		}
		filter.setDescricao(descricao);

		List<ProdutoDTO> listaDTO = new ArrayList<>();
		List<Produto> listaEntidades = new ArrayList<>();

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
			ProdutoDTO response = new ProdutoDTO();
			BeanUtils.copyProperties(item, response);
			listaDTO.add(response);
		});

		return listaDTO;
	}

	public List<ProdutoDTO> findAll() {
		List<ProdutoDTO> lista = new ArrayList<>();

		var lis = this.repository.findAll();

		lis.forEach(item -> {
			ProdutoDTO dto = new ProdutoDTO();
			BeanUtils.copyProperties(item, dto);
			lista.add(dto);
		});

		return lista;
	}

	@Transactional
	public ProdutoDTO criar(ProdutoDTO obj) {
		obj.setCode(400);
		var entity = new Produto();
		entity.setDescricao(obj.getDescricao());
		entity.setDataCriacao(LocalDateTime.now());
		entity.setSituacao(Boolean.TRUE);
		entity = this.repository.save(entity);

		if (obj.getIdsFornecedores() != null && !obj.getIdsFornecedores().isEmpty()) {
			for (Long item : obj.getIdsFornecedores()) {
				Long idForncedor = item;
				FornecedorProduto fornecedorProduto = new FornecedorProduto();
				fornecedorProduto.setFornecedor(fornecedorRepository.findByPorId(idForncedor));
				fornecedorProduto.setProduto(repository.findByProdutoId(entity.getId()));
				fornecedorProdutoRepository.save(fornecedorProduto);
			}
		} else {
			obj.setCode(400);
			obj.setMessage("Erro na operação.");
		}

		BeanUtils.copyProperties(entity, obj);
		obj.setCode(200);
		obj.setMessage("Operacao realizada com sucesso.");
		return obj;
	}

	@Transactional
	public ProdutoDTO update(ProdutoDTO obj) {
		var newObj = repository.findById(obj.getId());

		if (newObj.isPresent()) {
			var entity = newObj.get();
			entity.setDescricao(obj.getDescricao());
			entity.setDataAtualizacao(LocalDateTime.now());
			entity.setSituacao(Boolean.TRUE);
			entity = this.repository.save(entity);
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
		findById(id);
		try {
			this.repository.deleteById(id);
		} catch (Exception e) {
			throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
		}
	}

	public ProdutoDTO removerAssociacaoComProduto(Long idProduto, Long pessoaId) {
		ProdutoDTO res = new ProdutoDTO();
		res.setCode(400);
		try {
			var fornecedor = fornecedorRepository.findFornecedorByPessoaId(pessoaId);
			var forncedorProduto = fornecedorProdutoRepository.obterAssociacaoComProduto(idProduto, fornecedor.getId());
			fornecedorProdutoRepository.delete(forncedorProduto);
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
