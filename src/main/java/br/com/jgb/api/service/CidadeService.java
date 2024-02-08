package br.com.jgb.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jgb.api.dto.CidadeDTO;
import br.com.jgb.api.dto.EstadoDTO;
import br.com.jgb.api.exceptions.ObjectNotFoundException;
import br.com.jgb.api.repository.CidadeRepository;
import br.com.jgb.api.repository.EstadoRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repository;
	
	@Autowired
	private EstadoRepository estadoRepository;

	public CidadeDTO findById(Long id) {
		var dto = new CidadeDTO();
		
		var entity = this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Cidade não encontrada na base de dados."));
		
		 BeanUtils.copyProperties(entity, dto);
		 
		 return dto;
	}
	
	public List<CidadeDTO> obterCidadeByEstado(Long uf) {
		List<CidadeDTO> lista = new ArrayList<>();
		
		var lis = this.repository.findByIdEstado(uf);
		
		lis.forEach(item -> {
			CidadeDTO pessoa = new CidadeDTO();
			BeanUtils.copyProperties(item, pessoa);
			lista.add(pessoa);
		});
		
		return lista;
	}
	
	public List<CidadeDTO> findAll() {
		List<CidadeDTO> lista = new ArrayList<>();
		
		var lis = this.repository.findAll();
		
		lis.forEach(item -> {
			CidadeDTO pessoa = new CidadeDTO();
			BeanUtils.copyProperties(item, pessoa);
			lista.add(pessoa);
		});
		
		return lista;
	}
	
	
	public List<EstadoDTO> findUFs() {
		List<EstadoDTO> lista = new ArrayList<>();
		var entities = this.estadoRepository.findAll();
		
		entities.forEach(item -> {
			EstadoDTO dto = new EstadoDTO();
			BeanUtils.copyProperties(item, dto);
			lista.add(dto);
		});
		
		return lista;
	}

}
