package br.com.jgb.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jgb.api.dto.ClienteServicoDTO;
import br.com.jgb.api.entity.cliente.ClienteServico;
import br.com.jgb.api.exceptions.DataBindingViolationException;
import br.com.jgb.api.exceptions.ObjectNotFoundException;
import br.com.jgb.api.repository.ClienteServicoRepository;

@Service
public class ClienteServicoService {

	@Autowired
	private ClienteServicoRepository repository;

	public ClienteServicoDTO findById(Long id) {
		var dto = new ClienteServicoDTO();
		
		var pessoa = this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Serviço contratado não encontrado."));
		
		 BeanUtils.copyProperties(pessoa, dto);
		 
		 return dto;
	}
	
	public List<ClienteServicoDTO> findAll() {
		List<ClienteServicoDTO> lista = new ArrayList<>();
		
		var lis = this.repository.findAll();
		
		lis.forEach(item -> {
			ClienteServicoDTO pessoa = new ClienteServicoDTO();
			BeanUtils.copyProperties(item, pessoa);
			lista.add(pessoa);
		});
		
		return lista;
	}

	@Transactional
	public ClienteServicoDTO criar(ClienteServicoDTO pessoa) {
		var entity = new ClienteServico();
		 BeanUtils.copyProperties(pessoa, entity);
		 entity.setId(null);
		 entity = this.repository.save(entity);
		 BeanUtils.copyProperties(entity, pessoa);
		return pessoa;
	}

	@Transactional
	public ClienteServicoDTO update(ClienteServicoDTO obj) {
		var newObj = repository.findById(obj.getId());
		
		if(newObj.isPresent()) {
			var entity = newObj.get();
			entity.setId(obj.getId());
			entity.setServico(obj.getServico());
			entity = this.repository.save(entity);
			BeanUtils.copyProperties(entity, obj);
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
