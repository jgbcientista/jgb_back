package br.com.jgb.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.jgb.api.entity.agenda.Agenda;
import br.com.jgb.api.request.AgendaRequest;
import br.com.jgb.api.response.AgendaResponse;
import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor  
public class AgendaMapper {
    private final ModelMapper mapper;

    public Agenda toAgenda(AgendaRequest request) {
        return mapper.map(request, Agenda.class);
    }

    public AgendaResponse toAgendaResponse(Agenda agenda) {
        return mapper.map(agenda, AgendaResponse.class);
    }

    public List<AgendaResponse> toEventoResponseList(List<Agenda> agenda) {
        return agenda.stream()
                .map(this::toAgendaResponse)
                .collect(Collectors.toList());
    }
}
