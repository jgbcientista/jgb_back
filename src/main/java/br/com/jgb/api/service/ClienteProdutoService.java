package br.com.jgb.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jgb.api.dto.ClienteProdutoDTO;
import br.com.jgb.api.entity.cliente.ClienteProduto;
import br.com.jgb.api.exceptions.DataBindingViolationException;
import br.com.jgb.api.exceptions.ObjectNotFoundException;
import br.com.jgb.api.repository.ClienteProdutoRepository;

@Service
public class ClienteProdutoService {

	@Autowired
	private ClienteProdutoRepository repository;

	public ClienteProdutoDTO findById(Long id) {
		var dto = new ClienteProdutoDTO();
		
		var pessoa = this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Produto contratado não encontrado na base de dados."));
		 BeanUtils.copyProperties(pessoa, dto);
		 
		 return dto;
	}
	
	public List<ClienteProdutoDTO> findAll() {
		List<ClienteProdutoDTO> lista = new ArrayList<>();
		
		var lis = this.repository.findAll();
		
		lis.forEach(item -> {
			ClienteProdutoDTO dto = new ClienteProdutoDTO();
			BeanUtils.copyProperties(item, dto);
			lista.add(dto);
		});
		
		return lista;
	}

	@Transactional
	public ClienteProdutoDTO criar(ClienteProdutoDTO pessoa) {
		var entity = new ClienteProduto();
		 BeanUtils.copyProperties(pessoa, entity);
		 entity.setId(null);
		 entity = this.repository.save(entity);
		 BeanUtils.copyProperties(entity, pessoa);
		return pessoa;
	}

	@Transactional
	public ClienteProdutoDTO update(ClienteProdutoDTO obj) {
		var newObj = repository.findById(obj.getId());
		
		if(newObj.isPresent()) {
			var entity = newObj.get();
			entity.setId(obj.getId());
			entity.setProduto(obj.getProduto());
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
