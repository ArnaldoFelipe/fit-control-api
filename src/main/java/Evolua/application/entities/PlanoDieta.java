package Evolua.application.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Evolua.application.entities.enums.DiaDaSemana;
import Evolua.application.entities.enums.ObjetivoFitness;
import Evolua.application.exception.diaDieta.DiaDietaNaoEncontradoException;
import Evolua.application.exception.planoTreino.DiasDuplicadosException;
import Evolua.application.exception.planoTreino.PlanoInvalidoException;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "plano_dieta")
public class PlanoDieta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plano_dieta_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "objetivo_fitness", nullable = false)
    private ObjetivoFitness objetivoFitness;

    @Column(name = "calorias_diarias", nullable = false)
    private BigDecimal caloriasDiarias;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @OneToMany(
        mappedBy = "planoDieta",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<DiaDieta> dias = new ArrayList<>();

    @PrePersist
    public void PrePersist(){
        this.dataCriacao = LocalDateTime.now();
    }

    public PlanoDieta(){}

    public PlanoDieta(Usuario usuario, ObjetivoFitness objetivoFitness, BigDecimal caloriasDiarias) {

        if(usuario == null){
            throw new PlanoInvalidoException("usuario é obrigatorio");
        }
        if(objetivoFitness == null){
            throw new PlanoInvalidoException("objetivo é obrigatorio");
        }
        if(caloriasDiarias == null){
            throw new PlanoInvalidoException("calorias é obrigatorio");
        }
        
        this.usuario = usuario;
        this.objetivoFitness = objetivoFitness;
        this.caloriasDiarias = caloriasDiarias;
        this.ativo = true;
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public ObjetivoFitness getObjetivoFitness() {
        return objetivoFitness;
    }

    public BigDecimal getCaloriasDiarias() {
        return caloriasDiarias;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public List<DiaDieta> getDias() {
        return List.copyOf(dias);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PlanoDieta other = (PlanoDieta) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    private void validarDias(List<DiaDaSemana> diasSemana){

        if(diasSemana == null || diasSemana.isEmpty()){
            throw new PlanoInvalidoException("Plano deve possuir pelo menos um dia");
        }

        Set<DiaDaSemana> diasUnicos = new HashSet<>(diasSemana);

        if(diasUnicos.size() != diasSemana.size()){
            throw new DiasDuplicadosException("Dias de dieta não podem ser repetidos");
        }
    }

    public void adicionarDia(DiaDaSemana diaSemana) {

        boolean jaExiste = dias.stream()
            .anyMatch(d -> d.getDia().equals(diaSemana));

        if(jaExiste){
            throw new DiasDuplicadosException("dia ja existe no plano");
        }

        DiaDieta dia = new DiaDieta(this, diaSemana);
        dias.add(dia);
    }

    public DiaDieta buscarDia(DiaDaSemana diaSemana){
        return dias.stream()
            .filter(d -> d.getDia().equals(diaSemana))
            .findFirst()
            .orElseThrow(() -> new DiaDietaNaoEncontradoException("Dia não encontrado no plano"));
    }

    public PlanoDieta atualizarObjetivo(ObjetivoFitness objetivo){
        if(objetivo == null){
            throw new PlanoInvalidoException("Objetivo não pode ser nulo");
        }

        this.objetivoFitness = objetivo;

        return this;
    }

    public PlanoDieta atualizarCalorias(BigDecimal calorias){
        if(calorias == null){
            throw new PlanoInvalidoException("Objetivo não pode ser nulo");
        }

        this.caloriasDiarias = calorias;
        return this;
    }

    public void ativar(){
        this.ativo = true;
    }

    public void desativar(){
        this.ativo = false;
    }
}