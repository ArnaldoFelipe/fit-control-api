package Evolua.application.services;

import java.util.List;

import Evolua.application.dto.diaTreino.DiaTreinoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Evolua.application.dto.diaTreino.DiaTreinoResponse;
import Evolua.application.dto.planoTreino.AtualizarPlanoRequest;
import Evolua.application.dto.planoTreino.PlanoTreinoRequest;
import Evolua.application.dto.planoTreino.PlanoTreinoResponse;
import Evolua.application.dto.treino.AtualizarTreinoRequest;
import Evolua.application.dto.treino.TreinoRequest;
import Evolua.application.dto.treino.TreinoResponse;
import Evolua.application.entities.DiaTreino;
import Evolua.application.entities.Exercicio;
import Evolua.application.entities.PlanoTreino;
import Evolua.application.entities.Treino;
import Evolua.application.entities.Usuario;
import Evolua.application.entities.enums.DiaDaSemana;
import Evolua.application.exception.exercicio.ExercicioNaoEncontradoException;
import Evolua.application.exception.planoTreino.PlanoTreinoNaoEncontradoException;
import Evolua.application.exception.usuario.UsuarioNaoEncontradoException;
import Evolua.application.repository.ExercicioRepository;
import Evolua.application.repository.PlanoTreinoRepository;
import Evolua.application.repository.TreinoRepository;
import Evolua.application.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class PlanoTreinoService {
    
    @Autowired
    private PlanoTreinoRepository planoTreinoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ExercicioRepository exercicioRepository;

    @Autowired
    private TreinoRepository teinoRepository;

    public PlanoTreinoResponse toPlanoResponse(PlanoTreino planoTreino){

        List<DiaTreinoResponse> dias = planoTreino.getDias()
        .stream()
        .map(diaTreino -> {

            List<TreinoResponse> treinos = diaTreino.getTreinos()
                .stream()
                .map(this::toTreinoResponse)
                .toList();

            return new DiaTreinoResponse(
                diaTreino.getDiaDaSemana(),
                treinos
            );
        })
        .toList();

        return new PlanoTreinoResponse(
            planoTreino.getId(),
            planoTreino.getUsuario().getId(),
            planoTreino.getObjetivoFitness(),
            planoTreino.getVolumeTreino(),
            planoTreino.isAtivo(),
            planoTreino.getDataCriacao(),
            dias
        );
    }

    public PlanoTreino toPlanoEntity(PlanoTreinoRequest request, Usuario usuario){
        return new PlanoTreino(
            usuario,
            request.objetivoFitness(),
            request.volumeTreino()
        );
    }

    public TreinoResponse toTreinoResponse(Treino treino){
        return new TreinoResponse(
            treino.getId(),
            treino.getDiaTreino().getId(),
            treino.getExercicio().getId(),
            treino.getExercicio().getNome(),
            treino.getSeries(),
            treino.getRepeticoes()
        );
    }

    @Transactional
    public PlanoTreinoResponse criarPlanoTreino(PlanoTreinoRequest request){
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
            .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com id: " + request.usuarioId() + " não encontrado"));
        
        planoTreinoRepository.findByUsuarioAndAtivoTrue(usuario)
            .ifPresent(planoAtivo -> {
                planoAtivo.desativar();
                planoTreinoRepository.save(planoAtivo);
            });

        PlanoTreino plano = toPlanoEntity(request, usuario);

        for (DiaTreinoRequest diaRequest : request.dias()){

            DiaTreino dia = plano.adicionarDia(diaRequest.dia());

            List<Exercicio> exercicios = exercicioRepository.findByGrupoMuscularIn(diaRequest.gruposMusculares());

            int quantidadeExercicios = Math.min(5, exercicios.size());

            for(int i= 0 ; i<quantidadeExercicios; i++){
                dia.adicionarTreino(exercicios.get(i), 4, 10);
            }
        }

        PlanoTreino planoSalvo = planoTreinoRepository.save(plano);
        return toPlanoResponse(planoSalvo);
    }

    public PlanoTreinoResponse buscarPlanoAtivoPorUsuario(Long usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com id: " + usuarioId + " não encontrado"));

        PlanoTreino plano = planoTreinoRepository.findByUsuarioAndAtivoTrue(usuario)
            .orElseThrow(() -> new PlanoTreinoNaoEncontradoException("Usuario não possui plano ativo"));

        return toPlanoResponse(plano);
    }

    @Transactional
    public PlanoTreinoResponse atualizarPlano(Long id, AtualizarPlanoRequest request){

        PlanoTreino plano = planoTreinoRepository.findById(id)
            .orElseThrow(() -> new PlanoTreinoNaoEncontradoException("Plano não encontrado"));

        if (request.dias() != null) {
        plano.atualizarDias(request.dias());
        }

        if (request.volumeTreino() != null) {
            plano.atualizarVolume(request.volumeTreino());
        }

        if (request.objetivo() != null) {
            plano.atualizarObjetivo(request.objetivo());
        }

        return toPlanoResponse(plano);
    }

    @Transactional
    public TreinoResponse adicionarTreinoAoDia(Long planoId, DiaDaSemana diaSemana, TreinoRequest treino){

        PlanoTreino planoTreino = planoTreinoRepository.findById(planoId)
            .orElseThrow(() -> new PlanoTreinoNaoEncontradoException("Plano não encontrado"));

        DiaTreino dia = planoTreino.buscarDia(diaSemana);

        Exercicio exercicio = exercicioRepository.findById(treino.exercicioId())
            .orElseThrow(() -> new ExercicioNaoEncontradoException("exercicio não encontrado"));

        Treino treinoCriado = dia.adicionarTreino(exercicio, treino.series(), treino.repeticoes());

        return toTreinoResponse(treinoCriado);
    }

    @Transactional
    public TreinoResponse atualizarTreino(Long planoId, DiaDaSemana diaSemana, Long treinoId, AtualizarTreinoRequest treinoRequest){

        PlanoTreino planoTreino = planoTreinoRepository.findById(planoId)
            .orElseThrow(() -> new PlanoTreinoNaoEncontradoException("Plano não encontrado"));

        DiaTreino dia = planoTreino.buscarDia(diaSemana);

        Treino treino = dia.buscarTreinoPorId(treinoId);

        Exercicio exercicio = null;
        
        if(treinoRequest.exercicioId() != null){
            exercicio = exercicioRepository.findById(treinoRequest.exercicioId())
                .orElseThrow(() -> new ExercicioNaoEncontradoException("exercicio não encontrado"));
        }
        treino.atualizar(exercicio, treinoRequest.series(), treinoRequest.repeticoes());

        return toTreinoResponse(treino);
    }

    @Transactional
    public void removerTreinoAoDia(Long planoId, DiaDaSemana diaSemana, Long treinoId){

        PlanoTreino plano = planoTreinoRepository.findById(planoId)
            .orElseThrow(() -> new PlanoTreinoNaoEncontradoException("Plano não encontrado"));
        
        DiaTreino dia = plano.buscarDia(diaSemana);

        dia.removerTreino(treinoId);
    }

    public TreinoResponse buscarTreinoId(Long planoId, DiaDaSemana diaSemana, Long treinoId){
        PlanoTreino plano = planoTreinoRepository.findById(planoId)
            .orElseThrow(() -> new PlanoTreinoNaoEncontradoException("Plano não encontrado"));
        DiaTreino dia = plano.buscarDia(diaSemana);

        Treino treino = dia.buscarTreinoPorId(treinoId);
        return toTreinoResponse(treino);
    }

    public void criaTreinos(PlanoTreino planoTreino){

        List<Exercicio> exercicios = exercicioRepository.findAll();

        for(DiaTreino diaTreino : planoTreino.getDias()){

            for(Exercicio exercicio : exercicios){
                Treino treino = new Treino(
                    diaTreino,
                    exercicio,
                    4,
                    10
                );

                diaTreino.adicionarTreino(exercicio, 4, 10);
                teinoRepository.save(treino);
            }
        }
    }
}
