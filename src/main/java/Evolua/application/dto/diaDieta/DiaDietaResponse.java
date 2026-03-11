package Evolua.application.dto.diaDieta;

import java.util.List;

import Evolua.application.dto.refeicao.RefeicaoResponse;
import Evolua.application.entities.enums.DiaDaSemana;

public record DiaDietaResponse (
    
    DiaDaSemana dia,
    List<RefeicaoResponse> refeicoes
){}
