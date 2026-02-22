package Evolua.application.entities;

import java.math.BigDecimal;

import Evolua.application.entities.enums.TipoRefeicao;
import Evolua.application.exception.refeicao.RefeicaoInvalidaException;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "refeicao")
public class Refeicao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "dia_dieta_id")
    private DiaDieta diaDieta;

    @Enumerated(EnumType.STRING)
    private TipoRefeicao tpRefeicao;

    private String nome;
    private BigDecimal calorias;

    public Refeicao(){}

    public Refeicao(DiaDieta diaDieta, TipoRefeicao tpRefeicao, String nome, BigDecimal calorias) {

        validarRefeicao(diaDieta, tpRefeicao, nome, calorias);

        this.diaDieta = diaDieta;
        this.tpRefeicao = tpRefeicao;
        this.nome = nome;
        this.calorias = calorias;
    }

    public Long getId() {
        return id;
    }

    public DiaDieta getDiaDieta() {
        return diaDieta;
    }

    public TipoRefeicao getTpRefeicao() {
        return tpRefeicao;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getCalorias() {
        return calorias;
    }
    
    private void validarRefeicao(DiaDieta diaDieta, TipoRefeicao tpRefeicao, String nome, BigDecimal calorias){
        if(diaDieta == null){
            throw new RefeicaoInvalidaException("Dia da dieta é obrigatorio");
        }
        if(tpRefeicao == null){
            throw new RefeicaoInvalidaException("tipo de refeição é obrigatorio");
        }
        if(nome == null){
            throw new RefeicaoInvalidaException("nome da refeição é obrigatorio");
        }
        if(calorias != null && calorias.compareTo(BigDecimal.ZERO) < 0){
            throw new RefeicaoInvalidaException("Valor de calorias não pode ser negativo");
        }
    }

    public Refeicao atualizarRefeicao(TipoRefeicao tpRefeicao, String nome, BigDecimal calorias){
        if(tpRefeicao == null && nome == null && calorias == null){
            throw new RefeicaoInvalidaException("Nenhum campo informado para atualização");
        }
        if(calorias != null && calorias.compareTo(BigDecimal.ZERO) < 0){
        throw new RefeicaoInvalidaException("Valor de calorias não pode ser negativo");
        }
        if(tpRefeicao != null){
            this.tpRefeicao = tpRefeicao;
        }
        if(nome != null){
            this.nome = nome;
        }
        if(calorias != null){
            this.calorias = calorias;
        }
        return this;
    }
}
