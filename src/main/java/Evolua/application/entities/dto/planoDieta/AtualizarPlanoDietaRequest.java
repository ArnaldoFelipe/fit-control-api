package Evolua.application.entities.dto.planoDieta;

import java.math.BigDecimal;

import Evolua.application.entities.enums.ObjetivoFitness;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record AtualizarPlanoDietaRequest (

    @NotNull(message = "Objetivo é obrigatorio")
    ObjetivoFitness objetivoFitness,

    @PositiveOrZero
    BigDecimal caloriasDiarias

){}
