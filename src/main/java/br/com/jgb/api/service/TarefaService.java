package br.com.jgb.api.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jgb.api.dto.TarefaDTO;
import br.com.jgb.api.entity.enums.PerfilEnum;
import br.com.jgb.api.entity.rh.funcionario.Tarefa;
import br.com.jgb.api.entity.rh.pessoa.usuario.Usuario;
import br.com.jgb.api.exceptions.AuthorizationException;
import br.com.jgb.api.exceptions.DataBindingViolationException;
import br.com.jgb.api.exceptions.ObjectNotFoundException;
import br.com.jgb.api.repository.TarefaRepository;
import br.com.jgb.api.security.UserSpringSecurity;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioService usuarioService;

    public Tarefa findById(Long id) {
        Tarefa tarefa = this.tarefaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Tarefa não encontrado."));

        UserSpringSecurity userSpringSecurity = UsuarioService.authenticated();
        if (Objects.isNull(userSpringSecurity)
                || !userSpringSecurity.hasRole(PerfilEnum.USUARIO_COMUM) && !userHasTask(userSpringSecurity, tarefa))
            throw new AuthorizationException("Acesso negado!");

        return tarefa;
    }

    public List<TarefaDTO> findAllByUser() {
        UserSpringSecurity userSpringSecurity = UsuarioService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");

        List<TarefaDTO> tasks = this.tarefaRepository.findByUsuario_Id(userSpringSecurity.getId());
        return tasks;
    }

    @Transactional
    public Tarefa create(Tarefa obj) {
        UserSpringSecurity userSpringSecurity = UsuarioService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");

        Usuario usuario = this.usuarioService.findById(userSpringSecurity.getId());
        obj.setId(null);
        
        obj.setUsuario(usuario);
        obj = this.tarefaRepository.save(obj);
        return obj;
    }

    @Transactional
    public Tarefa update(Tarefa obj) {
        Tarefa newObj = findById(obj.getId());
        newObj.setDescricao(obj.getDescricao());
        return this.tarefaRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.tarefaRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
        }
    }

    private Boolean userHasTask(UserSpringSecurity userSpringSecurity, Tarefa tarefa) {
        return tarefa.getUsuario().getId().equals(userSpringSecurity.getId());
    }

}
