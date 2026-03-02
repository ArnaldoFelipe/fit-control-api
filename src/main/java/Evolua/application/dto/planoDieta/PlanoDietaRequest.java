package Evolua.application.dto.planoDieta;

import java.math.BigDecimal;
import java.util.List;

import Evolua.application.entities.enums.DiaDaSemana;
import Evolua.application.entities.enums.ObjetivoFitness;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record PlanoDietaRequest (
    @NotNull(message = "Usuario é obrigatorio")
    Long usuarioId, 

    @NotNull(message = "Objetivo é obrigatorio")
    ObjetivoFitness objetivoFitness,

    @PositiveOrZero
    BigDecimal caloriasDiarias,

    @NotEmpty(message = "Ionforme pelo menos um dia para o plano")
    List<DiaDaSemana> dias
){}
