package Evolua.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Evolua.application.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
    Optional<Usuario> findByEmail(String email);

    Boolean existsByEmail(String email);
}
