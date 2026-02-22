package Evolua.application.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import Evolua.application.entities.enums.DiaDaSemana;
import Evolua.application.entities.enums.TipoRefeicao;
import Evolua.application.exception.refeicao.RefeicaoNaoEncontradoException;
import Evolua.application.exception.refeicao.TipoRefeicaoDuplicadoException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "dia_dieta")
public class DiaDieta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dia_dieta_id")
    private Long id;

    @ManyToOne(optional= false)
    @JoinColumn(name = "plano_dieta_id", nullable = false)
    private PlanoDieta planoDieta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiaDaSemana dia;

    @OneToMany(
        mappedBy = "diaDieta",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Refeicao> refeicoes = new ArrayList<>();

    public Long getId() {
        return id;
    }
    public PlanoDieta getPlanoDieta() {
        return planoDieta;
    }

    public DiaDaSemana getDia() {
        return dia;
    }

    public List<Refeicao> getRefeicoes(){
        return List.copyOf(refeicoes);
    }

    public DiaDieta(){}

    public DiaDieta(PlanoDieta planoDieta, DiaDaSemana dia) {
        this.planoDieta = planoDieta;
        this.dia = dia;
    }

    public Refeicao adicionarRefeicao(TipoRefeicao tpRefeicao, String nome, BigDecimal calorias){

        boolean tipoJaExiste = refeicoes.stream()
            .anyMatch(r -> r.getTpRefeicao().equals(tpRefeicao));

        if(tipoJaExiste){
            throw new TipoRefeicaoDuplicadoException("Ja existe o tipo " + tpRefeicao + " nesse dia");
        }

        Refeicao refeicao = new Refeicao(this, tpRefeicao, nome, calorias);
        this.refeicoes.add(refeicao);
        return refeicao;
    }

    public Refeicao buscarRefeicaoPorId(Long refeicaoId){
        return refeicoes.stream()
            .filter(r -> r.getId().equals(refeicaoId))
            .findFirst()
            .orElseThrow(() -> new RefeicaoNaoEncontradoException("Refeição não existe nesse dia"));
    }

    public void removerRefeicao(Long refeicaoId){
        Refeicao refeicao = buscarRefeicaoPorId(refeicaoId);
        refeicoes.remove(refeicao);
    }

    public Refeicao atualizarRefeicao(
        Long refeicaoId,
        TipoRefeicao novoTipo,
        String novoNome,
        BigDecimal novasCalorias) {

        Refeicao refeicao = buscarRefeicaoPorId(refeicaoId);

        if (novoTipo != null) {
            boolean tipoJaExiste = refeicoes.stream()
                .anyMatch(r ->
                    !r.getId().equals(refeicaoId) &&
                    r.getTpRefeicao().equals(novoTipo)
                );

            if (tipoJaExiste) {
                throw new TipoRefeicaoDuplicadoException(
                    "Já existe o tipo " + novoTipo + " nesse dia"
                );
            }
        }

        refeicao.atualizarRefeicao(novoTipo, novoNome, novasCalorias);
        return refeicao;
    }
}
