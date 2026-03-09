package Evolua.application.dto.diaTreino;

import Evolua.application.entities.enums.DiaDaSemana;
import Evolua.application.entities.enums.GrupoMuscular;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DiaTreinoRequest(

        @NotNull(message = "Dia da semana é obrigatorio")
        DiaDaSemana dia,

        @NotNull(message = "Informe os grupos musculares")
        List<GrupoMuscular> gruposMusculares
) {}
