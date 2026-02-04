package Evolua.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Evolua.application.entities.DiaTreino;
import Evolua.application.entities.PlanoTreino;

public interface DiaTreinoRepository extends JpaRepository<DiaTreino, Long>{
    
    List<DiaTreino> findByPlanoTreino(PlanoTreino planoTreino);
}
