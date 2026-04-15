package Evolua.application.dto.usuario;

import java.math.BigDecimal;
import java.time.LocalDate;

import Evolua.application.entities.enums.ClassificacaoImc;

public record UsuarioResponse(

    Long id,
    String nome,
    String email,
    BigDecimal peso,
    BigDecimal altura,
    BigDecimal imc,
    ClassificacaoImc classificacaoImc,
    LocalDate dataNascimento,
    Integer idade
) {}
