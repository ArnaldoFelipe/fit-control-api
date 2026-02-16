package Evolua.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Evolua.application.entities.Usuario;
import Evolua.application.entities.dto.usuario.UsuarioRequest;
import Evolua.application.entities.dto.usuario.UsuarioResponse;
import Evolua.application.exception.usuario.EmailJaCadastradoException;
import Evolua.application.exception.usuario.UsuarioNaoEncontradoException;
import Evolua.application.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    private UsuarioResponse toResponse(Usuario usuario){
        return new UsuarioResponse(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail()
        );
    }

    private Usuario toEntity(UsuarioRequest request){
         Usuario usuario = new Usuario();
            usuario.setNome(request.nome());
            usuario.setSenha(request.senha());
            usuario.setEmail(request.email());
        
        return usuario;
    }

    @Transactional
    public UsuarioResponse criarUsuario(UsuarioRequest usuarioRequest){
        if(usuarioRepository.existsByEmail(usuarioRequest.email())){
            throw new EmailJaCadastradoException("Email ja cadastrado");
        }

        Usuario usuario = toEntity(usuarioRequest);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return toResponse(usuarioSalvo);
    }

    public UsuarioResponse buscarUsuarioPorId(Long id){

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException( "Usuário com id " + id + " não encontrado"));

        return toResponse(usuario);
    }

    public UsuarioResponse buscarUsuarioPorEmail(String email){

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com id " + email + " não encontrado"));

        return toResponse(usuario);
    }

}
