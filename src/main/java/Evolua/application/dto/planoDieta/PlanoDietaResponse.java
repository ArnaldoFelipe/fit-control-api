package Evolua.application.dto.planoDieta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import Evolua.application.entities.enums.DiaDaSemana;
import Evolua.application.entities.enums.ObjetivoFitness;

public record PlanoDietaResponse (

    Long planoId,
    Long usuarioId,
    ObjetivoFitness objetivoFitness,
    BigDecimal caloriasDiarias,
    Boolean ativo,
    LocalDateTime dataCriacao,
    List<DiaDaSemana> dias
){}
