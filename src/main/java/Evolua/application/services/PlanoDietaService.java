package Evolua.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Evolua.application.entities.DiaDieta;
import Evolua.application.entities.PlanoDieta;
import Evolua.application.entities.Refeicao;
import Evolua.application.entities.Usuario;
import Evolua.application.entities.dto.planoDieta.AtualizarPlanoDietaRequest;
import Evolua.application.entities.dto.planoDieta.PlanoDietaRequest;
import Evolua.application.entities.dto.planoDieta.PlanoDietaResponse;
import Evolua.application.entities.dto.refeicao.AtualizarRefeicaoRequest;
import Evolua.application.entities.dto.refeicao.RefeicaoRequest;
import Evolua.application.entities.dto.refeicao.RefeicaoResponse;
import Evolua.application.entities.enums.DiaDaSemana;
import Evolua.application.exception.planoDieta.PlanoDietaNaoEncontradoException;
import Evolua.application.exception.usuario.UsuarioNaoEncontradoException;
import Evolua.application.repository.PlanoDietaRepository;
import Evolua.application.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class PlanoDietaService {
    
    @Autowired
    private PlanoDietaRepository planoDietaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public PlanoDietaResponse toPlanoDietaResponse(PlanoDieta planoDieta){
        return new PlanoDietaResponse(
            planoDieta.getId(),
            planoDieta.getUsuario().getId(),
            planoDieta.getObjetivoFitness(),
            planoDieta.getCaloriasDiarias(),
            planoDieta.getAtivo(),
            planoDieta.getDataCriacao(),
            planoDieta.getDias()
                    .stream()
                    .map(DiaDieta::getDia)
                    .toList());
    }

    public PlanoDieta toPlanoDietaEntity(PlanoDietaRequest request, Usuario usuario){
        return new PlanoDieta(
            usuario,
            request.objetivoFitness(),
            request.caloriasDiarias(),
            request.dias()
        );
    }

    public RefeicaoResponse toRefeicaoResponse(Refeicao refeicao){
        return new RefeicaoResponse(
            refeicao.getId(),
            refeicao.getDiaDieta().getId(),
            refeicao.getTpRefeicao(),
            refeicao.getNome(),
            refeicao.getCalorias()
        );
    }

    @Transactional
    public PlanoDietaResponse criarPlano(PlanoDietaRequest request){

        Usuario usuario = usuarioRepository.findById(request.usuarioId())
            .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com id: " + request.usuarioId() + " não encontrado"));

        PlanoDieta plano = toPlanoDietaEntity(request, usuario);

        planoDietaRepository.save(plano);
        return toPlanoDietaResponse(plano);
    }

    public PlanoDietaResponse buscarPlanoPorId(Long planoId){

        PlanoDieta plano = planoDietaRepository.findById(planoId)
            .orElseThrow(() -> new PlanoDietaNaoEncontradoException("Plano não encontrado"));

        return toPlanoDietaResponse(plano);
    }

    @Transactional
    public PlanoDietaResponse atualizarPlano(Long planoId, AtualizarPlanoDietaRequest request){

        PlanoDieta plano = planoDietaRepository.findById(planoId)
            .orElseThrow(() -> new PlanoDietaNaoEncontradoException("Plano não encontrado"));

        plano.atualizarObjetivo(request.objetivoFitness());
        plano.atualizarCalorias(request.caloriasDiarias());

        return toPlanoDietaResponse(plano);
    }

    @Transactional
    public void desativarPlano(Long planoId){

        PlanoDieta plano = planoDietaRepository.findById(planoId)
            .orElseThrow(() -> new PlanoDietaNaoEncontradoException("Plano não encontrado"));

        plano.desativar();
    }

    @Transactional
    public RefeicaoResponse adicionarRefeicao(Long planoId, DiaDaSemana diaSemana, RefeicaoRequest request){
        
        PlanoDieta plano = planoDietaRepository.findById(planoId)
            .orElseThrow(() -> new PlanoDietaNaoEncontradoException("Plano não encontrado")); 

        DiaDieta dia = plano.buscarDia(diaSemana);

        Refeicao refeicao = dia.adicionarRefeicao(
            request.tpRefeicao(), 
            request.nome(), 
            request.calorias());

        return toRefeicaoResponse(refeicao);
    }

    @Transactional
    public RefeicaoResponse atualizarRefeicao(Long planoId, DiaDaSemana diaSemana, Long refeicaoId, AtualizarRefeicaoRequest request){

        PlanoDieta plano = planoDietaRepository.findById(planoId)
            .orElseThrow(() -> new PlanoDietaNaoEncontradoException("Plano não encontrado"));

        DiaDieta dia = plano.buscarDia(diaSemana);

        Refeicao refeicao = dia.atualizarRefeicao(
            refeicaoId,
            request.tpRefeicao(), 
            request.nome(), 
            request.calorias());

        return toRefeicaoResponse(refeicao);
    }

    @Transactional
    public void removerRefeicao(Long planoId, DiaDaSemana diaSemana, Long refeicaoId){

        PlanoDieta plano = planoDietaRepository.findById(planoId)
            .orElseThrow(() -> new PlanoDietaNaoEncontradoException("Plano não encontrado"));
        
        DiaDieta dia = plano.buscarDia(diaSemana);

        dia.removerRefeicao(refeicaoId);
    }
}