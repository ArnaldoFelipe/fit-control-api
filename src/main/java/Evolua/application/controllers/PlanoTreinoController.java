package Evolua.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Evolua.application.entities.dto.planoTreino.PlanoTreinoRequest;
import Evolua.application.entities.dto.planoTreino.PlanoTreinoResponse;
import Evolua.application.services.PlanoTreinoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/planos")
public class PlanoTreinoController {
    
    @Autowired
    private PlanoTreinoService planoTreinoService;

    @PostMapping
    public ResponseEntity<PlanoTreinoResponse> criarPlanoTreino(@Valid @RequestBody PlanoTreinoRequest request){
        PlanoTreinoResponse planoTreino = planoTreinoService.criarPlanoTreino(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(planoTreino);
    }

    @GetMapping("/ativo/{usuarioId}")
    public ResponseEntity<PlanoTreinoResponse> buscarPlanoAtivo(@Positive @PathVariable Long usuarioId){
        PlanoTreinoResponse planoAtivo = planoTreinoService.buscarPlanoAtivoPorUsuario(usuarioId);
        return ResponseEntity.ok(planoAtivo);
    }
}
