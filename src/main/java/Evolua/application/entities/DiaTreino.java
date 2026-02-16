package Evolua.application.entities;

import Evolua.application.entities.enums.DiaDaSemana;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "dia_treino",
       uniqueConstraints = @UniqueConstraint(
            columnNames = {"plano_id", "diaDaSemana"}
       )
)
public class DiaTreino {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dia_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "plano_id", nullable = false)
    private PlanoTreino planoTreino;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiaDaSemana diaDaSemana;

    public DiaTreino(){}

    public DiaTreino(PlanoTreino planoTreino, DiaDaSemana diaDaSemana) {
        this.planoTreino = planoTreino;
        this.diaDaSemana = diaDaSemana;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlanoTreino getPlanoTreino() {
        return planoTreino;
    }

    public void setPlanoTreino(PlanoTreino planoTreino) {
        this.planoTreino = planoTreino;
    }

    public DiaDaSemana getDiaDaSemana() {
        return diaDaSemana;
    }

    public void setDiaDaSemana(DiaDaSemana diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
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
        DiaTreino other = (DiaTreino) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
