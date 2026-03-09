package Evolua.application.arnold.dto;

import Evolua.application.arnold.enums.TipoRespostaArnold;
import Evolua.application.dto.planoDieta.PlanoDietaRequest;
import Evolua.application.dto.planoTreino.PlanoTreinoRequest;

public record ArnoldDecisao(
    TipoRespostaArnold tipoResposta,
    String mensagem,
    PlanoTreinoRequest planoTreino,
    PlanoDietaRequest planoDieta
) {}
