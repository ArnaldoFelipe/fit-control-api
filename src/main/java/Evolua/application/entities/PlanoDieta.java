package Evolua.application.entities;

import java.time.LocalDateTime;

import Evolua.application.entities.enums.ObjetivoFitness;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "plano_dieta")
public class PlanoDieta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private ObjetivoFitness objetivoFitness;

    private Integer caloriasDiarias;

    private Boolean ativo;

    private LocalDateTime dataCriacao;

    @PrePersist
    public void PrePersist(){
        this.dataCriacao = LocalDateTime.now();
    }

    public PlanoDieta(){}

    public PlanoDieta(Usuario usuario, ObjetivoFitness objetivoFitness, Integer caloriasDiarias,
            Boolean ativo) {
        this.usuario = usuario;
        this.objetivoFitness = objetivoFitness;
        this.caloriasDiarias = caloriasDiarias;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ObjetivoFitness getObjetivoFitness() {
        return objetivoFitness;
    }

    public void setObjetivoFitness(ObjetivoFitness objetivoFitness) {
        this.objetivoFitness = objetivoFitness;
    }

    public Integer getCaloriasDiarias() {
        return caloriasDiarias;
    }

    public void setCaloriasDiarias(Integer caloriasDiarias) {
        this.caloriasDiarias = caloriasDiarias;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
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
}
