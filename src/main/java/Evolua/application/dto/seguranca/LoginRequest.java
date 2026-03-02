package Evolua.application.dto.seguranca;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
    
    @NotEmpty(message = "email não pode ser nulo")
    String email,
    @NotEmpty(message = "senha não pode ser nulo")
    String senha
) {}
