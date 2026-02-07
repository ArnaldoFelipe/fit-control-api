package Evolua.application.entities.dto.usuario;

import Evolua.application.entities.enums.ObjetivoFitness;

public record UsuarioResponse(

    Long id,
    String nome,
    String email,
    ObjetivoFitness objetivo
) {}
