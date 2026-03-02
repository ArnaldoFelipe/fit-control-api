package Evolua.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import Evolua.application.exception.usuario.UsuarioNaoEncontradoException;
import Evolua.application.repository.UsuarioRepository;

public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario não existe"));
    }
}
