package Evolua.application.repository;

import java.util.List;

import Evolua.application.entities.enums.GrupoMuscular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Evolua.application.entities.Exercicio;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long>{
    
    @Query("SELECT e.nome FROM Exercicio e")
    List<String> findAllNomes();

    List<Exercicio> findByGrupoMuscular(GrupoMuscular grupoMuscular);

    List<Exercicio> findByGrupoMuscularIn(List<GrupoMuscular> grupos);

}
