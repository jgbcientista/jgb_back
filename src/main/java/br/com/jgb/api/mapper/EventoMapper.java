package br.com.jgb.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.jgb.api.entity.agenda.Evento;
import br.com.jgb.api.request.EventoRequest;
import br.com.jgb.api.response.EventoResponse;
import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor  
public class EventoMapper {

    private final ModelMapper mapper;

    public Evento toEvento(EventoRequest request) {
        return mapper.map(request, Evento.class);//eu quero transforma essa request em Evento.class
    }

    public EventoResponse toEventoResponse(Evento evento) {
        return mapper.map(evento, EventoResponse.class);
    }
    public List<EventoResponse> toEventoResponseList(List<Evento> eventos) {
        return eventos.stream()
                .map(this::toEventoResponse)
                .collect(Collectors.toList());
    }
}
