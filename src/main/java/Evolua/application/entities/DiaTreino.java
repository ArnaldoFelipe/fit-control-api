package Evolua.application.entities;

import java.util.ArrayList;
import java.util.List;

import Evolua.application.entities.enums.DiaDaSemana;
import Evolua.application.exception.treino.TreinoNaoEncontradoException;
import Evolua.application.exception.treino.TreinoDuplicadoException;
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

    @OneToMany(
        mappedBy = "diaTreino",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Treino> treinos = new ArrayList<>();

    public DiaTreino(){}

    public DiaTreino(PlanoTreino planoTreino, DiaDaSemana diaDaSemana) {
        this.planoTreino = planoTreino;
        this.diaDaSemana = diaDaSemana;
    }

    public Long getId() {
        return id;
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

    public List<Treino> getTreinos() {
        return List.copyOf(treinos);
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

    public Treino adicionarTreino(Exercicio exercicio, Integer series, Integer repeticoes){

        Boolean jaExiste = treinos.stream()
            .anyMatch(t -> t.getExercicio().equals(exercicio));

        if(jaExiste){
            throw new TreinoDuplicadoException("Exercicio ja adicionado neste dia");
        }

        Treino treino = new Treino(this, exercicio, series, repeticoes);
        this.treinos.add(treino);
        return treino;
    }

    public void removerTreino(Long treinoId){
        Treino treino = treinos.stream()
                .filter(t -> t.getId().equals(treinoId))
                .findFirst()
                .orElseThrow(() -> new TreinoNaoEncontradoException("Treino não encontrado neste dia"));
        treinos.remove(treino);        
    }

    public Treino buscarTreinoPorId(Long treinoId){
        return treinos.stream()
                .filter(t -> t.getId().equals(treinoId))
                .findFirst()
                .orElseThrow(() -> new TreinoNaoEncontradoException("Treino não encontrado neste dia"));
    }
}
