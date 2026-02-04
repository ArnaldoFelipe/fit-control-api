package Evolua.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Evolua.application.entities.DiaTreino;
import Evolua.application.entities.Treino;

public interface TreinoRepository extends JpaRepository<Treino, Long>{
    
    List<Treino> findByDiaTreino(DiaTreino diaTreino);
}
