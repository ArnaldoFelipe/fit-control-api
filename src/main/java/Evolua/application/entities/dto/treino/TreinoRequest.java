package Evolua.application.entities.dto.treino;

import jakarta.validation.constraints.NotNull;

public record TreinoRequest (

    @NotNull(message = "Dia do treino é obrigatorio")
    Long diaTreinoId,

    @NotNull(message = "exercicio é obrigatorio")
    Long exercicioId,

    @NotNull(message = "Quantidade de series é obrigatorio")
    Integer series,

    @NotNull(message = "Quantidade de repeticoes é obrigatorio")
    Integer repeticoes
){}
