package Evolua.application.dto.diaTreino;

import Evolua.application.dto.treino.TreinoRequest;
import Evolua.application.entities.enums.DiaDaSemana;
import Evolua.application.entities.enums.GrupoMuscular;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DiaTreinoRequest(

        @NotNull(message = "Dia da semana é obrigatorio")
        DiaDaSemana dia,

        @NotNull(message = "Informe os grupos musculares")
        List<String> gruposMuscular,

        
        List<TreinoRequest> exercicios
) {

        public List<GrupoMuscular> gruposMusculares() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'gruposMusculares'");
        }}
