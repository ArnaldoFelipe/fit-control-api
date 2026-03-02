package Evolua.application.dto.refeicao;

import java.math.BigDecimal;

import Evolua.application.entities.enums.TipoRefeicao;

public record RefeicaoResponse(

    Long refeicaoId,
    Long diaDietaId,
    TipoRefeicao tpRefeicao,
    String nome,
    BigDecimal calorias
) {}
