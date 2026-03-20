package Evolua.application.dto.usuario;

import java.math.BigDecimal;

import Evolua.application.entities.enums.ClassificacaoImc;

public record UsuarioResponse(

    Long id,
    String nome,
    String email,
    BigDecimal peso,
    BigDecimal Altura,
    BigDecimal imc,
    ClassificacaoImc classificacaoImc
) {}
