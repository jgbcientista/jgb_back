package br.com.jgb.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.jgb.api.entity.agenda.Evento;
import br.com.jgb.api.exceptions.BussinessException;
import br.com.jgb.api.repository.EventoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional // toda vez que houver algum problema na transação ele não salva no banco
@RequiredArgsConstructor // ele cria um construtor , com todas as dependencias, e a variavel deve ser
							// declarada com final(quer dizer que você não pode atribuir valor duas vezes à
							// variáve)
public class EventoService {

	private final EventoRepository repository;

	public Evento salvar(Evento paciente) {

		boolean existeCpf = false;
		Optional<Evento> optionalPaciente = repository.findByCpf(paciente.getCpf());
		if (optionalPaciente.isPresent()) {
			if (!optionalPaciente.get().getId().equals(paciente.getId())) {
				existeCpf = true;
			}
		}
		if (existeCpf) {
			throw new RuntimeException("CPF já cadastrado.");
		}

		return repository.save(paciente);
	}

	public Evento alterar(Long id, Evento paciente) {
		Optional<Evento> optionalPaciente = this.findById(id);

		if (optionalPaciente.isEmpty()) {
			throw new BussinessException("Evento não cadastrado.");
		}
		paciente.setId(id);
		return salvar(paciente);
	}

	public List<Evento> findAll() {
		return repository.findAll();
	}

	public Optional<Evento> findById(Long id) {
		return repository.findById(id);
	}

	public void deletar(Long id) {
		repository.deleteById(id);
	}

}