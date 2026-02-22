package Evolua.application.entities.dto.refeicao;

import java.math.BigDecimal;

import Evolua.application.entities.enums.TipoRefeicao;

public record AtualizarRefeicaoRequest(

    Long refeicaoId,
    TipoRefeicao tpRefeicao,
    String nome, 
    BigDecimal calorias
) {}
