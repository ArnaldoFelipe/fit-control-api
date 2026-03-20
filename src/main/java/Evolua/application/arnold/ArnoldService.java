package Evolua.application.arnold;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Evolua.application.arnold.dto.ArnoldDecisao;
import Evolua.application.arnold.dto.ArnoldResponse;
import Evolua.application.dto.planoDieta.PlanoDietaRequest;
import Evolua.application.dto.planoDieta.PlanoDietaResponse;
import Evolua.application.dto.planoTreino.PlanoTreinoRequest;
import Evolua.application.dto.planoTreino.PlanoTreinoResponse;
import Evolua.application.entities.Usuario;
import Evolua.application.exception.arnold.ArnoldAiException;
import Evolua.application.exception.usuario.UsuarioNaoEncontradoException;
import Evolua.application.repository.UsuarioRepository;
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
    @Autowired
    private UsuarioRepository usuarioRepository;
        
    public ArnoldResponse interagir(String mensagemDoUsuario, Long usuarioId){

        
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException( "Usuário não encontrado"));

        String mensagemComContexto = """
            DADOS DO USUÁRIO:
            - usuarioId: %d
            - peso: %s
            - altura: %s
            - imc: %s
            - classificacaoImc: %s

            MENSAGEM DO USUÁRIO:
            %s
            """.formatted(
                usuario.getId(),
                usuario.getPeso() != null ? usuario.getPeso() : "desconhecido",
                usuario.getAltura() != null ? usuario.getAltura() : "desconhecido",
                usuario.getImc() != null ? usuario.getImc() : "desconhecido",
                usuario.getClassificacaoImc() != null ? usuario.getClassificacaoImc() : "desconhecido",
                mensagemDoUsuario
        );

        ArnoldDecisao decisao = arnoldAiService.processar(usuarioId, mensagemComContexto);

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
