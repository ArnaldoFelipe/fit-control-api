package Evolua.application.entities;

import java.time.LocalDateTime;

import Evolua.application.entities.enums.ObjetivoFitness;
import Evolua.application.entities.enums.VolumeTreino;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "plano_treino")
public class PlanoTreino {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private ObjetivoFitness objetivoFitness;

    private Integer diasPorSemana;

    @Enumerated(EnumType.STRING)
    private VolumeTreino VolumeTreino;

    private boolean ativo;

    private LocalDateTime dataCriacao;

    @PrePersist
    public void PrePersist(){
        this.dataCriacao = LocalDateTime.now();
    }

    public PlanoTreino(){}

    public PlanoTreino(Long id, Usuario usuario, ObjetivoFitness objetivoFitness, Integer diasPorSemana, VolumeTreino volumeTreino, boolean ativo) {
        this.id = id;
        this.usuario = usuario;
        this.objetivoFitness = objetivoFitness;
        this.diasPorSemana = diasPorSemana;
        VolumeTreino = volumeTreino;
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

    public Integer getDiasPorSemana() {
        return diasPorSemana;
    }

    public void setDiasPorSemana(Integer diasPorSemana) {
        this.diasPorSemana = diasPorSemana;
    }

    public VolumeTreino getVolumeTreino() {
        return VolumeTreino;
    }

    public void setVolumeTreino(VolumeTreino volumeTreino) {
        VolumeTreino = volumeTreino;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
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
        PlanoTreino other = (PlanoTreino) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
