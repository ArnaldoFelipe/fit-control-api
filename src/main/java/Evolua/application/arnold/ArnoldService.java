package Evolua.application.arnold;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Evolua.application.arnold.dto.ArnoldDecisao;
import Evolua.application.arnold.dto.ArnoldResponse;
import Evolua.application.dto.planoDieta.PlanoDietaRequest;
import Evolua.application.dto.planoDieta.PlanoDietaResponse;
import Evolua.application.dto.planoTreino.PlanoTreinoRequest;
import Evolua.application.dto.planoTreino.PlanoTreinoResponse;
import Evolua.application.exception.arnold.ArnoldAiException;
import Evolua.application.services.PlanoDietaService;
import Evolua.application.services.PlanoTreinoService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ArnoldService {

    @Autowired
    private ArnoldAiService arnoldAiService;
    @Autowired
    private PlanoTreinoService treinoService;
    @Autowired
    private PlanoDietaService dietaService;
        

    public ArnoldResponse interagir(String mensagemDoUsuario, Long usuarioId){

        ArnoldDecisao decisao = arnoldAiService.processar(usuarioId, mensagemDoUsuario);

        log.info("Resposta IA: {}", decisao);

        switch (decisao.tipoResposta()) {

            case CRIAR_TREINO -> {
                log.info("Criando treino para o usuario {}", usuarioId);

                var request = new PlanoTreinoRequest(
                    usuarioId,
                    decisao.planoTreino().objetivoFitness(),
                    decisao.planoTreino().volumeTreino(),
                    decisao.planoTreino().dias()
                );

                PlanoTreinoResponse planoCriado = treinoService.criarPlanoTreinoIA(request);

                return new ArnoldResponse(
                    decisao.tipoResposta(),
                    decisao.mensagem(),
                    planoCriado,
                    null
                );
            }

            case CRIAR_DIETA  -> {
                log.info("Criando dieta para o usuario {}", usuarioId);

                var request = new PlanoDietaRequest(
                    usuarioId,
                    decisao.planoDieta().objetivoFitness(),
                    decisao.planoDieta().caloriasDiarias(),
                    decisao.planoDieta().dias()
                ); 

                PlanoDietaResponse dietaCriada = dietaService.criarPlano(request);

                return new ArnoldResponse(
                    decisao.tipoResposta(),
                    decisao.mensagem(),
                    null,
                    dietaCriada
                );
            }

            case CHAT -> {
                log.info("Apenas conversa para o usuario {}", usuarioId);

                return new ArnoldResponse(
                    decisao.tipoResposta(),
                    decisao.mensagem(),
                    null,
                    null
                );
            }
        }
        throw new ArnoldAiException("Erro ao comunicar com o serviço de IA");     
    }
}
