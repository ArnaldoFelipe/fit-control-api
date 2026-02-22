package Evolua.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Evolua.application.entities.dto.planoDieta.AtualizarPlanoDietaRequest;
import Evolua.application.entities.dto.planoDieta.PlanoDietaRequest;
import Evolua.application.entities.dto.planoDieta.PlanoDietaResponse;
import Evolua.application.entities.dto.refeicao.AtualizarRefeicaoRequest;
import Evolua.application.entities.dto.refeicao.RefeicaoRequest;
import Evolua.application.entities.dto.refeicao.RefeicaoResponse;
import Evolua.application.entities.enums.DiaDaSemana;
import Evolua.application.services.PlanoDietaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/planosDieta")
public class PlanoDietaController {
    
    @Autowired
    private PlanoDietaService planoDietaService;

    @PostMapping
    public ResponseEntity<PlanoDietaResponse> criarPlano(@Valid @RequestBody PlanoDietaRequest request){
        PlanoDietaResponse plano = planoDietaService.criarPlano(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(plano);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoDietaResponse> buscarPlanoPorId(@PathVariable Long planoId){
        PlanoDietaResponse plano = planoDietaService.buscarPlanoPorId(planoId);
        return ResponseEntity.ok(plano);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanoDietaResponse> atualizarPlano(@PathVariable Long planoId, @RequestBody AtualizarPlanoDietaRequest request){
        PlanoDietaResponse plano = planoDietaService.atualizarPlano(planoId, request);
        return ResponseEntity.ok(plano);
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativarPlano(@PathVariable Long planoId){
        planoDietaService.desativarPlano(planoId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/dias/{dia}/refeicoes")
    public ResponseEntity<RefeicaoResponse> adicionarRefeicao(@PathVariable Long id, @PathVariable DiaDaSemana diaSemana,@Valid @RequestBody RefeicaoRequest request){
        
        RefeicaoResponse refeicao = planoDietaService.adicionarRefeicao(id, diaSemana, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(refeicao);
    }

    @PatchMapping("/{id}/dias/{dia}/refeicoes/{refeicaoId}")
    public ResponseEntity<RefeicaoResponse> atualizarRefeicao(@PathVariable Long id, @PathVariable DiaDaSemana diaSemana, @PathVariable Long refeicaoId, @Valid @RequestBody AtualizarRefeicaoRequest request){
        RefeicaoResponse refeicao = planoDietaService.atualizarRefeicao(id, diaSemana, refeicaoId, request);
        return ResponseEntity.ok(refeicao);
    }

    @DeleteMapping("/{id}/dias/{dia}/refeicoes/{refeicaoId}")
    public ResponseEntity<Void> removerRefeicao(@PathVariable Long id, @PathVariable DiaDaSemana diaSemana, @PathVariable Long refeicaoId){
        planoDietaService.removerRefeicao(id, diaSemana, refeicaoId);
        return ResponseEntity.noContent().build();
    }
}