package Evolua.application.entities.dto.planoTreino;

import Evolua.application.entities.enums.ObjetivoFitness;
import Evolua.application.entities.enums.VolumeTreino;
import jakarta.validation.constraints.NotNull;

public record PlanoTreinoRequest(
    @NotNull(message = "Usuario é obrigatorio")
    Long usuarioId, 

    @NotNull(message = "Objetivo é obrigatorio")
    ObjetivoFitness objetivoFitness,

    @NotNull(message = "Frequencia é obrigatorio")
    Integer diasPorSemana,

    @NotNull(message = "Volume de treino é obrigatorio")
    VolumeTreino volumeTreino
){}    

