package Evolua.application.dto.diaDieta;

import Evolua.application.dto.refeicao.RefeicaoRequest;
import Evolua.application.entities.enums.DiaDaSemana;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DiaDietaRequest(

        @NotNull
        DiaDaSemana dia,

        @NotEmpty
        List<RefeicaoRequest> refeicoes
) {}
