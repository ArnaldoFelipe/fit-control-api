package Evolua.application.entities.dto.planoTreino;

import java.util.List;

import Evolua.application.entities.enums.DiaDaSemana;
import Evolua.application.entities.enums.ObjetivoFitness;
import Evolua.application.entities.enums.VolumeTreino;

public record AtualizarPlanoRequest(
    List<DiaDaSemana> dias,
    VolumeTreino volumeTreino,
    ObjetivoFitness objetivo
) {}
