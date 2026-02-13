package Evolua.application.entities.dto.planoTreino;

import java.time.LocalDateTime;

import Evolua.application.entities.enums.ObjetivoFitness;
import Evolua.application.entities.enums.VolumeTreino;


public record PlanoTreinoResponse(
    Long id,
    Long usuarioId,
    ObjetivoFitness objetivoFitness,
    Integer diasPorSemana,
    VolumeTreino volumeTreino,
    boolean ativo,
    LocalDateTime dataCriacao
) {}
