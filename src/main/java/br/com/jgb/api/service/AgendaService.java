package br.com.jgb.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.jgb.api.entity.agenda.Agenda;
import br.com.jgb.api.entity.agenda.Evento;
import br.com.jgb.api.exceptions.BussinessException;
import br.com.jgb.api.repository.AgendaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional 
@RequiredArgsConstructor
public class AgendaService {

    private final AgendaRepository agendaRepository;
    private final EventoService eventoService;

    public List<Agenda> findAll(){
        return  agendaRepository.findAll();
    }

    public Optional<Agenda> buscarPorId(Long id){
        return agendaRepository.findById(id);
    }

    public Agenda salvar(Agenda agenda){
        Optional<Evento> optionalEvento = eventoService.findById(agenda.getEvento().getId());

        if(optionalEvento.isEmpty()){
            throw new BussinessException("Evento não encontrado na base de dados.");
        }
        Optional<Agenda> byHorario = agendaRepository.findByHorario(agenda.getHorario());

        if(byHorario.isPresent()){ 
            throw new BussinessException("Já existe agendamento para esse horário.");
        }
        agenda.setEvento(optionalEvento.get());
        agenda.setDataCriacao(LocalDateTime.now());

        return agendaRepository.save(agenda);

    }







}