package Evolua.application.entities;


import Evolua.application.exception.treino.TreinoInvalidoException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "treino")
public class Treino {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "dia_id", nullable = false)
    private DiaTreino diaTreino;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exercicio_id", nullable = false)
    private Exercicio exercicio;

    private Integer series;
    private Integer repeticoes;
    
    public Treino(){}

    public Treino(DiaTreino diaTreino, Exercicio exercicio, Integer series, Integer repeticoes) {

        validarTreino(diaTreino, exercicio, series, repeticoes);

        this.diaTreino = diaTreino;
        this.exercicio = exercicio;
        this.series = series;
        this.repeticoes = repeticoes;
    }

    public Long getId() {
        return id;
    }

    public DiaTreino getDiaTreino() {
        return diaTreino;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public Integer getSeries() {
        return series;
    }

    public Integer getRepeticoes() {
        return repeticoes;
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
        Treino other = (Treino) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    private void validarTreino(DiaTreino diaTreino, Exercicio exercicio, Integer series, Integer repeticoes){

        if(diaTreino == null){
            throw new TreinoInvalidoException("Dia de treino é obrigatorio");
        }
        if(exercicio == null){
            throw new TreinoInvalidoException("Exercicio é obrigatorio");
        }
        if(series == null || series <=0){
            throw new TreinoInvalidoException("Quantidade de series é obrigatorio");
        }
        if(repeticoes == null || repeticoes <=0){
            throw new TreinoInvalidoException("Quantidade de repetições é obrigatorio");
        }
    }

    public Treino atualizar(Exercicio exercicio, Integer series, Integer repeticoes){

        if (exercicio == null && series == null && repeticoes == null) {
            throw new TreinoInvalidoException("Nenhum campo informado para atualização");
        }
        if(exercicio != null){
            this.exercicio = exercicio;
        }
        if (series != null) {
            if (series <= 0) {
                throw new TreinoInvalidoException(
                    "Quantidade de séries deve ser maior que zero"
                );
            }
            this.series = series;
        }
        if (repeticoes != null) {
            if (repeticoes <= 0) {
                throw new TreinoInvalidoException(
                    "Quantidade de repetições deve ser maior que zero"
                );
            }
            this.repeticoes = repeticoes;
        }

        return this;
    }
}