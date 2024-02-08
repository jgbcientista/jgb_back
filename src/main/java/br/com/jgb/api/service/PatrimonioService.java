package br.com.jgb.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jgb.api.dto.PatrimonioDTO;
import br.com.jgb.api.entity.patrimonio.Patrimonio;
import br.com.jgb.api.exceptions.DataBindingViolationException;
import br.com.jgb.api.exceptions.ObjectNotFoundException;
import br.com.jgb.api.filter.PatrimonioFilter;
import br.com.jgb.api.repository.PatrimonioRepository;
import br.com.jgb.api.util.Util;

@Service
public class PatrimonioService {

	@Autowired
	private PatrimonioRepository repository;
	
	
	public List<PatrimonioDTO> findFilter(String codigo, String descricao, String tipoPatrimonio) {
		var filter = new PatrimonioFilter();
		
		filter.setCodigo(codigo);
		filter.setDescricao(descricao);
		filter.setTipoPatrimonio(tipoPatrimonio);
		
		List<PatrimonioDTO> listaDTO = new ArrayList<>();
		List<Patrimonio> listaEntidades = null;
			
		if(!Util.isNull(filter.getTipoPatrimonio()) && Util.isNull(codigo) && Util.isNull(filter.getDescricao())) {
			listaEntidades = this.repository.findFilterTipoPatrimonio(filter.getTipoPatrimonio());
		} else if(Util.isNull(filter.getTipoPatrimonio()) && !Util.isNull(codigo) && Util.isNull(filter.getDescricao())) {
			listaEntidades = this.repository.findFilterCodigo(filter.getCodigo());
		} else if(Util.isNull(filter.getTipoPatrimonio()) && Util.isNull(codigo) && !Util.isNull(filter.getDescricao())) {
			listaEntidades = this.repository.findFilterByDescricao(filter.getDescricao());
		} else if(!Util.isNull(filter.getTipoPatrimonio()) && !Util.isNull(codigo) && !Util.isNull(filter.getDescricao())) {
			listaEntidades = this.repository.findFilterByDescricaoAndTipoPatrimonioAndCodigo(filter.getDescricao(), filter.getTipoPatrimonio(), filter.getCodigo());
		} else if(!Util.isNull(filter.getTipoPatrimonio()) && Util.isNull(codigo) && !Util.isNull(filter.getDescricao())) {
			listaEntidades = this.repository.findFilterByDescricaoAndTipoPatromonio(filter.getDescricao(), filter.getTipoPatrimonio() );
		} else if(!Util.isNull(filter.getTipoPatrimonio()) && !Util.isNull(codigo) && Util.isNull(filter.getDescricao())) {
			listaEntidades = this.repository.findFilterByTipoPatrimonioAndCodigo(filter.getTipoPatrimonio(), filter.getCodigo());
		} else if(Util.isNull(filter.getTipoPatrimonio()) && !Util.isNull(filter.getDescricao()) && !Util.isNull(codigo)) {
			listaEntidades = this.repository.findFilterByDescricaoAndCodigo(filter.getDescricao(), filter.getCodigo() );
		} else {
			return findAll();
		}
		
		listaEntidades.forEach(item -> {
			PatrimonioDTO response = new PatrimonioDTO();
			BeanUtils.copyProperties(item, response);
			listaDTO.add(response);
		});
		return listaDTO;
	}

	public PatrimonioDTO findById(Long id) {
		var dto = new PatrimonioDTO();
		
		var pessoa = this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Serviço não encontrado."));
		
		 BeanUtils.copyProperties(pessoa, dto);
		 
		 return dto;
	}
	
	public List<PatrimonioDTO> findAll() {
		List<PatrimonioDTO> lista = new ArrayList<>();
		
		var lis = this.repository.findAll();
		
		lis.forEach(item -> {
			PatrimonioDTO pessoa = new PatrimonioDTO();
			BeanUtils.copyProperties(item, pessoa);
			lista.add(pessoa);
		});
		
		return lista;
	}

	@Transactional
	public PatrimonioDTO criar(PatrimonioDTO dto) {
		var entity = new Patrimonio();
		 dto.setCode(400);
		 BeanUtils.copyProperties(dto, entity);
		 entity.setId(null);
		 entity.setSituacao(Boolean.TRUE);
		 entity.setDataCriacao(LocalDateTime.now());
		 entity = this.repository.save(entity);
		 BeanUtils.copyProperties(entity, dto);
		 dto.setCode(200);
		 dto.setMessage("Operacao realizada com sucesso.");
		return dto;
	}

	@Transactional
	public PatrimonioDTO update(PatrimonioDTO obj) {
		var newObj = repository.findById(obj.getId());
		
		if(newObj.isPresent()) {
			var entity = newObj.get();
			entity.setId(obj.getId());
			entity.setCodigo(obj.getCodigo());
			entity.setTipoPatrimonio(obj.getTipoPatrimonio());
			entity.setDescricao(obj.getDescricao());
		    entity.setDataAtualizacao(LocalDateTime.now());
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
