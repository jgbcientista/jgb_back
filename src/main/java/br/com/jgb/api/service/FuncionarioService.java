package br.com.jgb.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jgb.api.dto.FuncionarioDTO;
import br.com.jgb.api.entity.rh.funcionario.Funcionario;
import br.com.jgb.api.exceptions.DataBindingViolationException;
import br.com.jgb.api.exceptions.ObjectNotFoundException;
import br.com.jgb.api.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	public FuncionarioDTO findById(Long id) {
		FuncionarioDTO dto = new FuncionarioDTO();

		var pessoa = this.funcionarioRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Pesssoa não encontrada"));

		BeanUtils.copyProperties(pessoa, dto);

		return dto;
	}

	public List<FuncionarioDTO> findAll() {
		List<FuncionarioDTO> lista = new ArrayList<>();

		var lis = this.funcionarioRepository.findAll();

		lis.forEach(item -> {
			FuncionarioDTO pessoa = new FuncionarioDTO();
			BeanUtils.copyProperties(item, pessoa);
			lista.add(pessoa);
		});

		return lista;
	}

	@Transactional
	public FuncionarioDTO criar(FuncionarioDTO pessoa) {
		Funcionario entity = new Funcionario();
		BeanUtils.copyProperties(pessoa, entity);
		entity.setId(null);
		entity.setDataCriacao(LocalDateTime.now());
		entity = this.funcionarioRepository.save(entity);
		BeanUtils.copyProperties(entity, pessoa);
		return pessoa;
	}

	public void delete(Long id) {
		Optional<Funcionario> entity = this.funcionarioRepository.findById(id);
		try {
			if (entity.isPresent()) {
				entity.get().setSituacao(Boolean.FALSE);
				this.funcionarioRepository.save(entity.get());
			}
		} catch (Exception e) {
			throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
		}
	}

}
