package Evolua.application.entities.dto.planoTreino;

import java.util.List;

import Evolua.application.entities.enums.ObjetivoFitness;
import Evolua.application.entities.enums.VolumeTreino;
import Evolua.application.entities.enums.DiaDaSemana;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PlanoTreinoRequest(
    @NotNull(message = "Usuario é obrigatorio")
    Long usuarioId, 

    @NotNull(message = "Objetivo é obrigatorio")
    ObjetivoFitness objetivoFitness,

    @NotNull(message = "Volume de treino é obrigatorio")
    VolumeTreino volumeTreino,

    @NotEmpty(message = "Ionforme pelo menos um dia de Treino")
    List<DiaDaSemana> dias
){}    

