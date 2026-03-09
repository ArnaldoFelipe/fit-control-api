package Evolua.application.dto.planoTreino;

import java.time.LocalDateTime;
import java.util.List;

import Evolua.application.dto.diaTreino.DiaTreinoResponse;
import Evolua.application.entities.enums.ObjetivoFitness;
import Evolua.application.entities.enums.VolumeTreino;


public record PlanoTreinoResponse(
    Long id,
    Long usuarioId,
    ObjetivoFitness objetivoFitness,
    VolumeTreino volumeTreino,
    boolean ativo,
    LocalDateTime dataCriacao,
    List<DiaTreinoResponse> dias
) {}
