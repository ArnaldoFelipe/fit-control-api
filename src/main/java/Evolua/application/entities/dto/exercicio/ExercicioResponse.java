package Evolua.application.entities.dto.exercicio;

import Evolua.application.entities.enums.GrupoMuscular;

public record ExercicioResponse(
    Long id,
    String nome,
    GrupoMuscular grupoMuscular
){}
