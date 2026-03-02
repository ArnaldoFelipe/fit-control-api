package Evolua.application.dto.treino;

public record AtualizarTreinoRequest(
    Long exercicioId,
    Integer series,
    Integer repeticoes
) {}
