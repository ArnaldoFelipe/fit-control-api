package Evolua.application.arnold.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public record ArnoldChatRequest(
    @NotBlank(message = "A mensagem não pode estar vazia")
    @JsonProperty("mensagem")
    String mensagem
) {}
