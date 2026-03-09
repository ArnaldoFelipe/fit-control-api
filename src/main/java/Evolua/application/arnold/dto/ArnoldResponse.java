package Evolua.application.arnold.dto;

import Evolua.application.arnold.enums.TipoRespostaArnold;
import Evolua.application.dto.planoDieta.PlanoDietaResponse;
import Evolua.application.dto.planoTreino.PlanoTreinoResponse;

public record ArnoldResponse(
    TipoRespostaArnold tipoResposta,
    String mensagem,
    PlanoTreinoResponse planoTreino,
    PlanoDietaResponse planoDieta
) {}
