package br.com.jgb.api.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.jgb.api.entity.rh.pessoa.usuario.Usuario;
import br.com.jgb.api.repository.UsuarioRepository;
import br.com.jgb.api.security.UserSpringSecurity;

@Service
public class UsuarioDetalheServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = this.usuarioRepository.findByLogin(username);
        if (Objects.isNull(usuario))
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        return new UserSpringSecurity(usuario.getId(), usuario.getLogin(), usuario.getSenha(), usuario.getProfiles());
    }

}
