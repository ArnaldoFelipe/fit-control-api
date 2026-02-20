package Evolua.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Evolua.application.entities.Exercicio;
import Evolua.application.entities.dto.exercicio.ExercicioResponse;
import Evolua.application.exception.exercicio.ExercicioNaoEncontradoException;
import Evolua.application.repository.ExercicioRepository;

@Service
public class ExercicioService {
    
    @Autowired
    private ExercicioRepository exercicioRepository;

    public ExercicioResponse toResponse(Exercicio exercicio){
        return new ExercicioResponse(
            exercicio.getId(),
            exercicio.getNome(),
            exercicio.getGrupoMuscular()
        );
    }

    public List<ExercicioResponse> listarExercicios(){
        return exercicioRepository.findAll()
                    .stream()
                    .map(exercicio -> toResponse(exercicio))
                    .toList();
    }

    public ExercicioResponse buscarExercicioPorId(Long id){
        Exercicio exercicio = exercicioRepository.findById(id)
                    .orElseThrow(() -> new  ExercicioNaoEncontradoException("Exercicio não encontrado"));
        return toResponse(exercicio);
    }
}
