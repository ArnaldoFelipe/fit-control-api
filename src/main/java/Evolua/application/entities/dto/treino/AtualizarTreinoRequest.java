package Evolua.application.entities.dto.treino;

public record AtualizarTreinoRequest(
    Long exercicioId,
    Integer series,
    Integer repeticoes
) {}
