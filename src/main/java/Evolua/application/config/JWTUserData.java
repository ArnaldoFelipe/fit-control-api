package Evolua.application.config;

import lombok.Builder;

@Builder
public record JWTUserData(
    Long usuarioId,
    String nome
) {}
