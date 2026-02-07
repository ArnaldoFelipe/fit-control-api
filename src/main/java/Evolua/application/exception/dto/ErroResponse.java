package Evolua.application.exception.dto;

import java.time.LocalDateTime;

public record ErroResponse(
    String mensagem,
    String codigo,
    LocalDateTime timesTamp
) {}
