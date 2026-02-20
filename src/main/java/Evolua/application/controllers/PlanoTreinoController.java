package Evolua.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Evolua.application.entities.dto.planoTreino.AtualizarPlanoRequest;
import Evolua.application.entities.dto.planoTreino.PlanoTreinoRequest;
import Evolua.application.entities.dto.planoTreino.PlanoTreinoResponse;
import Evolua.application.entities.dto.treino.AtualizarTreinoRequest;
import Evolua.application.entities.dto.treino.TreinoRequest;
import Evolua.application.entities.dto.treino.TreinoResponse;
import Evolua.application.entities.enums.DiaDaSemana;
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

    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<PlanoTreinoResponse> atualizarPlano(@PathVariable Long id, @RequestBody AtualizarPlanoRequest request){
        PlanoTreinoResponse plano = planoTreinoService.atualizarPlano(id, request);
        return ResponseEntity.ok(plano);
    }

    @PostMapping("/{planoId}/dias/{dia}/treinos")
    public ResponseEntity<TreinoResponse> adicionarTreino(@PathVariable Long planoId, @PathVariable DiaDaSemana dia, @RequestBody @Valid TreinoRequest request){
        TreinoResponse treino = planoTreinoService.adicionarTreinoAoDia(planoId, dia, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(treino);
    }

    @PatchMapping("/{planoId}/dias/{dia}/treinos/{treinoId}")
    public ResponseEntity<TreinoResponse> atualizarTreino(@PathVariable Long planoId,
        @PathVariable DiaDaSemana dia,
        @PathVariable Long treinoId,
        @RequestBody @Valid AtualizarTreinoRequest request){

            TreinoResponse treino = planoTreinoService.atualizarTreino(planoId, dia, treinoId, request);
            return ResponseEntity.ok(treino);
        }

    @DeleteMapping("/{planoId}/dias/{dia}/treinos/{treinoId}")
    public ResponseEntity<Void> removerTreino(@PathVariable Long planoId, @PathVariable DiaDaSemana dia, @PathVariable Long treinoId){
        planoTreinoService.removerTreinoAoDia(planoId, dia, treinoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{planoId}/dias/{dia}/treinos/{treinoId}")
    public ResponseEntity<TreinoResponse> buscarTreinoPorId(@PathVariable Long planoId, @PathVariable DiaDaSemana dia, @PathVariable Long treinoId){
        TreinoResponse treino = planoTreinoService.buscarTreinoId(planoId, dia, treinoId);
        return ResponseEntity.ok(treino);
    }
}
