package Evolua.application.dto.diaTreino;

import java.util.List;

import Evolua.application.dto.treino.TreinoResponse;
import Evolua.application.entities.enums.DiaDaSemana;
import Evolua.application.entities.enums.GrupoMuscular;

public record DiaTreinoResponse(
    DiaDaSemana dia,
    GrupoMuscular grupoMuscular,
    List<TreinoResponse> treinos
) {}
