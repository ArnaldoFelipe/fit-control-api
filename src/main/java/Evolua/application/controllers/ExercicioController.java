package Evolua.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Evolua.application.entities.dto.exercicio.ExercicioResponse;
import Evolua.application.services.ExercicioService;

@RestController
@RequestMapping("/exercicios")
public class ExercicioController {
    
    @Autowired
    private ExercicioService exercicioService;

    @GetMapping
    public ResponseEntity<List<ExercicioResponse>> listarExercicios(){
        return ResponseEntity.ok(exercicioService.listarExercicios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExercicioResponse> buscarExercicioPorId(@PathVariable Long id){
        return ResponseEntity.ok(exercicioService.buscarExercicioPorId(id));
    }
}
