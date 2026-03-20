package Evolua.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Evolua.application.config.TokenConfig;
import Evolua.application.dto.seguranca.LoginRequest;
import Evolua.application.dto.seguranca.LoginResponse;
import Evolua.application.dto.usuario.UsuarioRequest;
import Evolua.application.dto.usuario.UsuarioResponse;
import Evolua.application.entities.Usuario;
import Evolua.application.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenConfig tokenConfig;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest login){
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(login.email(), login.senha());
        
        Authentication authentication = authenticationManager.authenticate(userAndPass);
        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = tokenConfig.generateToken(usuario);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity<UsuarioResponse> cadastrar(@Valid @RequestBody UsuarioRequest request){

        Usuario usuario = new Usuario(
            request.nome(),
            request.email(),
            passwordEncoder.encode(request.senha())
        );
        usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioResponse(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getPeso(),
            usuario.getAltura(),
            usuario.getImc(),
            usuario.getClassificacaoImc()
        ));
    }
}
