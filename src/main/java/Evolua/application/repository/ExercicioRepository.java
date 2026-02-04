package Evolua.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Evolua.application.entities.Exercicio;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long>{
    
    Optional<Exercicio> findByNome(String nome);

}
