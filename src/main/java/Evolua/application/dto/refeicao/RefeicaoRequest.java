package Evolua.application.dto.refeicao;

import java.math.BigDecimal;

import Evolua.application.entities.enums.TipoRefeicao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record RefeicaoRequest(

    @NotNull(message = "Tipo da refeição é obrigatorio")
    TipoRefeicao tpRefeicao,

    @NotBlank(message = "Nome da refeicao é obrigatorio")
    String nome, 

    @PositiveOrZero
    BigDecimal calorias
) {}
