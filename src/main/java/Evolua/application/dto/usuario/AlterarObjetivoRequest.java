package Evolua.application.dto.usuario;

import Evolua.application.entities.enums.ObjetivoFitness;
import jakarta.validation.constraints.NotNull;

public record AlterarObjetivoRequest (
    @NotNull
    ObjetivoFitness objetivo
){}

