package Evolua.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Evolua.application.entities.PlanoDieta;
import Evolua.application.entities.Usuario;

public interface PlanoDietaRepository extends JpaRepository<PlanoDieta, Long>{
    
    Optional<PlanoDieta> findByUsuarioAndAtivoTrue(Usuario usuario);
}
