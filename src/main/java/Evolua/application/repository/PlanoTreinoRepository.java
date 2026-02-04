package Evolua.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Evolua.application.entities.PlanoTreino;
import Evolua.application.entities.Usuario;

public interface PlanoTreinoRepository extends JpaRepository<PlanoTreino, Long>{
    
    Optional<PlanoTreino> findByUsuarioAndAtivoTrue(Usuario usuario);
}
