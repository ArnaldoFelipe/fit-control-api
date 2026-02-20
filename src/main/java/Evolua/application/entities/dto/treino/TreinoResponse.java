package Evolua.application.entities.dto.treino;

public record TreinoResponse(

    Long id,
    Long diaTreinoId,
    Long exercicioId,
    String nomeExercicio,
    Integer series,
    Integer repeticoes
) {}
