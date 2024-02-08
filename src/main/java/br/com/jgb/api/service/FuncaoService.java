package br.com.jgb.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jgb.api.dto.FuncaoDTO;
import br.com.jgb.api.entity.rh.funcionario.Funcao;
import br.com.jgb.api.exceptions.DataBindingViolationException;
import br.com.jgb.api.exceptions.ObjectNotFoundException;
import br.com.jgb.api.filter.ProdutoFilter;
import br.com.jgb.api.repository.FuncaoRepository;
import br.com.jgb.api.util.Util;

@Service
public class FuncaoService {

	@Autowired
	private FuncaoRepository repository;

	public FuncaoDTO findById(Long id) {
		var dto = new FuncaoDTO();
		
		var entity = this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Função não encontrada."));
		
		 BeanUtils.copyProperties(entity, dto);
		 
		 return dto;
	}
	
	public List<FuncaoDTO> findFilter(String codigo, String descricao) {
		var filter = new ProdutoFilter();
		
		if(!Util.isNull(codigo)) {
			filter.setCodigo(Integer.valueOf(codigo));
		}
		filter.setDescricao(descricao);
		
		List<FuncaoDTO> listaDTO = new ArrayList<>();
		List<Funcao> listaEntidades = null;
			
		if(!Util.isNull(filter.getDescricao()) && Util.isNull(codigo)) {
			listaEntidades = this.repository.findFilterByDescricao(filter.getDescricao());
		} else if(!Util.isNull(codigo) && Util.isNull(filter.getDescricao())) {
			listaEntidades = this.repository.findFilterByCodigo(filter.getCodigo());
		} else if(!Util.isNull(filter.getDescricao()) &&! Util.isNull(codigo)) {
			listaEntidades = this.repository.findFilterByDescricao(filter.getDescricao());
		}   else {
			return findAll();
		}
		listaEntidades.forEach(item -> {
			FuncaoDTO response = new FuncaoDTO();
			BeanUtils.copyProperties(item, response);
			listaDTO.add(response);
		});
 
		return listaDTO;
	}
	
	public List<FuncaoDTO> findAll() {
		List<FuncaoDTO> lista = new ArrayList<>();
		
		var lis = this.repository.findAll();
		
		lis.forEach(item -> {
			FuncaoDTO pessoa = new FuncaoDTO();
			BeanUtils.copyProperties(item, pessoa);
			lista.add(pessoa);
		});
		
		return lista;
	}

	@Transactional
	public FuncaoDTO criar(FuncaoDTO dto) {
		dto.setCode(400);
		var entity = new Funcao();
		 entity.setId(null);
		 entity.setDescricao(dto.getDescricao());
		 entity.setSituacao(Boolean.TRUE);
		 entity = this.repository.save(entity);
		 BeanUtils.copyProperties(entity, dto);
		 dto.setCode(200);
		 dto.setMessage("Operacao realizada com sucesso.");
		return dto;
	}

	@Transactional
	public FuncaoDTO update(FuncaoDTO obj) {
		var newObj = repository.findById(obj.getId());

		if(newObj.isPresent()) {
			var entity = newObj.get();
			entity.setId(obj.getId());
			entity.setDescricao(obj.getDescricao());
			entity = this.repository.save(entity);
			BeanUtils.copyProperties(entity, obj);
			obj.setCode(200);
			obj.setMessage("Operacao realizada com sucesso.");
		}else {
			obj.setCode(400);
			obj.setMessage("O campo ID da função não foi encontrado.");
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
}
