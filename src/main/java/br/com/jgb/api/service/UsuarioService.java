package br.com.jgb.api.service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jgb.api.dto.UsuarioDTO;
import br.com.jgb.api.entity.enums.PerfilEnum;
import br.com.jgb.api.entity.rh.pessoa.usuario.Usuario;
import br.com.jgb.api.exceptions.AuthorizationException;
import br.com.jgb.api.exceptions.DataBindingViolationException;
import br.com.jgb.api.exceptions.ObjectNotFoundException;
import br.com.jgb.api.repository.UsuarioRepository;
import br.com.jgb.api.security.UserSpringSecurity;
import jakarta.validation.Valid;

@Service
public class UsuarioService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public Usuario findById(Long id) {
        UserSpringSecurity userSpringSecurity = authenticated();
        if (!Objects.nonNull(userSpringSecurity)
                || !userSpringSecurity.hasRole(PerfilEnum.USUARIO_ADMIN) && !id.equals(userSpringSecurity.getId()))
            throw new AuthorizationException("Acesso negado!");

        Optional<Usuario> usuario = this.usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado."));
    }
    
    
    @Transactional
    public Usuario create(Usuario obj) {
        obj.setId(null);
        obj.setLogin(obj.getLogin());
        obj.setDataAtualizacao(LocalDateTime.now());
        obj.setSenha(this.bCryptPasswordEncoder.encode(obj.getSenha()));
        obj.setPerfis(Stream.of(PerfilEnum.USUARIO_ADMIN.getCode()).collect(Collectors.toSet()));
        obj = this.usuarioRepository.save(obj);
        return obj;
    }

    @Transactional
    public Usuario update(Usuario obj) {
        Usuario newObj = findById(obj.getId());
        newObj.setSenha(obj.getSenha());
        newObj.setSenha(this.bCryptPasswordEncoder.encode(obj.getSenha()));
        return this.usuarioRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.usuarioRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
        }
    }

    public static UserSpringSecurity authenticated() {
        try {
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    public Usuario fromDTO(@Valid UsuarioDTO obj) {
        Usuario usuario = new Usuario();
        usuario.setLogin(obj.getLogin());
        usuario.setSenha(obj.getSenha());
        return usuario;
    }

}
