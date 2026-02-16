package Evolua.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Evolua.application.entities.DiaTreino;
import Evolua.application.entities.PlanoTreino;
import Evolua.application.entities.Usuario;
import Evolua.application.entities.dto.planoTreino.AtualizarPlanoRequest;
import Evolua.application.entities.dto.planoTreino.PlanoTreinoRequest;
import Evolua.application.entities.dto.planoTreino.PlanoTreinoResponse;
import Evolua.application.entities.enums.DiaDaSemana;
import Evolua.application.exception.planoTreino.PlanoTreinoNaoEncontradoException;
import Evolua.application.exception.usuario.UsuarioNaoEncontradoException;
import Evolua.application.repository.PlanoTreinoRepository;
import Evolua.application.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class PlanoTreinoService {
    
    @Autowired
    PlanoTreinoRepository planoTreinoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    PlanoTreinoResponse toResponse(PlanoTreino planoTreino){

        List<DiaDaSemana> dias = planoTreino.getDias()
            .stream()
            .map(DiaTreino::getDiaDaSemana)
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

    PlanoTreino toEntity(PlanoTreinoRequest request, Usuario usuario){
        PlanoTreino planoTreino = new PlanoTreino();
        planoTreino.setUsuario(usuario);
        planoTreino.setObjetivoFitness(request.objetivoFitness());
        planoTreino.setVolumeTreino(request.volumeTreino());
        planoTreino.setAtivo(true);

        return planoTreino;
    }

    @Transactional
    public PlanoTreinoResponse criarPlanoTreino(PlanoTreinoRequest request){
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
            .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com id: " + request.usuarioId() + " não encontrado"));
        
        planoTreinoRepository.findByUsuarioAndAtivoTrue(usuario)
            .ifPresent(planoAtivo -> {
                planoAtivo.setAtivo(false);
                planoTreinoRepository.save(planoAtivo);
            });

        PlanoTreino planoTreino = toEntity(request, usuario);
        PlanoTreino planoSalvo = planoTreinoRepository.save(planoTreino);

        return toResponse(planoSalvo);
    }

    public PlanoTreinoResponse buscarPlanoAtivoPorUsuario(Long usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com id: " + usuarioId + " não encontrado"));

        PlanoTreino plano = planoTreinoRepository.findByUsuarioAndAtivoTrue(usuario)
            .orElseThrow(() -> new PlanoTreinoNaoEncontradoException("Usuario não possui plano ativo"));

        return toResponse(plano);
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
            plano.atulizarObjetivo(request.objetivo());
        }

        planoTreinoRepository.save(plano);
        return toResponse(plano);
    }
}
