package Evolua.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Evolua.application.entities.PlanoTreino;
import Evolua.application.entities.Usuario;
import Evolua.application.entities.dto.planoTreino.PlanoTreinoRequest;
import Evolua.application.entities.dto.planoTreino.PlanoTreinoResponse;
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
        return new PlanoTreinoResponse(
            planoTreino.getId(),
            planoTreino.getUsuario().getId(),
            planoTreino.getObjetivoFitness(),
            planoTreino.getDiasPorSemana(),
            planoTreino.getVolumeTreino(),
            planoTreino.isAtivo(),
            planoTreino.getDataCriacao()
        );
    }

    PlanoTreino toEntity(PlanoTreinoRequest request, Usuario usuario){
        PlanoTreino planoTreino = new PlanoTreino();
        planoTreino.setUsuario(usuario);
        planoTreino.setObjetivoFitness(request.objetivoFitness());
        planoTreino.setDiasPorSemana(request.diasPorSemana());
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
}
