package br.com.jgb.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jgb.api.dto.PerfilDTO;
import br.com.jgb.api.entity.rh.pessoa.usuario.Perfil;
import br.com.jgb.api.exceptions.DataBindingViolationException;
import br.com.jgb.api.exceptions.ObjectNotFoundException;
import br.com.jgb.api.filter.ProdutoFilter;
import br.com.jgb.api.repository.PerfilRepository;
import br.com.jgb.api.util.Util;

@Service
public class PerfilService {

	@Autowired
	private PerfilRepository repository;

	public PerfilDTO findById(Long id) {
		var dto = new PerfilDTO();
		
		var pessoa = this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Perfil não encontrado."));
		
		 BeanUtils.copyProperties(pessoa, dto);
		 
		 return dto;
	}
	
	public List<PerfilDTO> findFilter(String codigo, String descricao) {
		var filter = new ProdutoFilter();
		
		if(!Util.isNull(codigo)) {
			filter.setCodigo(Integer.valueOf(codigo));
		}
		filter.setDescricao(descricao);
		
		List<PerfilDTO> listaDTO = new ArrayList<>();
		List<Perfil> listaEntidades = null;
			
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
			PerfilDTO response = new PerfilDTO();
			BeanUtils.copyProperties(item, response);
			listaDTO.add(response);
		});
 
		return listaDTO;
	}
	
	public List<PerfilDTO> findAll() {
		List<PerfilDTO> lista = new ArrayList<>();
		
		var lis = this.repository.findAll();
		
		lis.forEach(item -> {
			PerfilDTO dto = new PerfilDTO();
			BeanUtils.copyProperties(item, dto);
			lista.add(dto);
		});
		
		return lista;
	}

	@Transactional
	public PerfilDTO criar(PerfilDTO dto) {
		var entity = new Perfil();
		 dto.setCode(400);
		 BeanUtils.copyProperties(dto, entity);
		 entity.setDataCriacao(LocalDateTime.now());
		 entity.setSituacao(Boolean.TRUE);
		 entity.setNome("ROLE_"+ dto.getNome().toUpperCase());
		 entity.setId(null);
		 entity = this.repository.save(entity);
		 BeanUtils.copyProperties(entity, dto);
		 dto.setCode(200);
		 dto.setMessage("Operacao realizada com sucesso.");
		 
		return dto;
	}

	@Transactional
	public PerfilDTO update(PerfilDTO obj) {
		var newObj = repository.findById(obj.getId());
		
		if(newObj.isPresent()) {
			var entity = newObj.get();
			entity.setId(obj.getId());
			
			if(obj.getNome().contains("ROLE")) {
				entity.setNome(obj.getNome().toUpperCase());
			}else {
				entity.setNome("ROLE_"+ obj.getNome().toUpperCase());
			}
			
			entity.setDescricao(obj.getDescricao());
			entity = this.repository.save(entity);
			BeanUtils.copyProperties(entity, obj);
			obj.setCode(200);
			obj.setMessage("Operacao realizada com sucesso.");
		}else {
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
}
