package Evolua.application.dto.diaTreino;

import java.util.List;

import Evolua.application.dto.treino.TreinoResponse;
import Evolua.application.entities.enums.DiaDaSemana;

public record DiaTreinoResponse(
    DiaDaSemana dia,
    List<TreinoResponse> treinos
) {}
