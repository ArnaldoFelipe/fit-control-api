package Evolua.application.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Evolua.application.entities.enums.DiaDaSemana;
import Evolua.application.entities.enums.ObjetivoFitness;
import Evolua.application.entities.enums.VolumeTreino;
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
@Table(name = "plano_treino")
public class PlanoTreino {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plano_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private ObjetivoFitness objetivoFitness;

    @Enumerated(EnumType.STRING)
    private VolumeTreino volumeTreino;

    private boolean ativo;

    private LocalDateTime dataCriacao;

    @OneToMany(
        mappedBy = "planoTreino",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<DiaTreino> dias = new ArrayList<>();

    
    @PrePersist
    public void prePersist(){
        this.dataCriacao = LocalDateTime.now();
    }

    public PlanoTreino(){}

    public PlanoTreino(Usuario usuario, ObjetivoFitness objetivoFitness, VolumeTreino volumeTreino, List<DiaDaSemana> diasSemana) {

        validarDias(diasSemana);

        this.usuario = usuario;
        this.objetivoFitness = objetivoFitness;
        this.volumeTreino = volumeTreino;
        this.ativo = true;

        for (DiaDaSemana diaSemana : diasSemana){
            DiaTreino dia = new DiaTreino();
            dia.setDiaDaSemana(diaSemana);
            this.adicionarDia(dia);
        }
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

    public VolumeTreino getVolumeTreino() {
        return volumeTreino;
    }

    public void setVolumeTreino(VolumeTreino volumeTreino) {
        this.volumeTreino = volumeTreino;
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

    public List<DiaTreino> getDias() {
        return dias;
    }

    public void setDias(List<DiaTreino> dias) {
        this.dias.clear();
        for (DiaTreino dia : dias){
            adicionarDia(dia);
        }
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

    public void adicionarDia(DiaTreino dia) {
    dias.add(dia);
    dia.setPlanoTreino(this);
    }

    private void validarDias(List<DiaDaSemana> diasSemana){

        if(diasSemana == null || diasSemana.isEmpty()){
            throw new PlanoInvalidoException("Plano deve possuir pelo menos um dia");
        }

        Set<DiaDaSemana> diasUnicos = new HashSet<>();

        if(diasUnicos.size() != diasSemana.size()){
            throw new DiasDuplicadosException("Dias de treino não podem ser repetidos");
        }
    }

    public void atualizarDias(List<DiaDaSemana> novosDias){
        validarDias(novosDias);

        this.dias.clear();

        for(DiaDaSemana diaSemana : novosDias){
            DiaTreino dia = new DiaTreino();
            dia.setDiaDaSemana(diaSemana);
            this.adicionarDia(dia);
        }
    }

    public void atualizarVolume(VolumeTreino novVolume){
        if(novVolume == null){
            throw new PlanoInvalidoException("Volume de treino não pode ser nulo");
        }

        this.volumeTreino = novVolume;
    }

    public void atulizarObjetivo(ObjetivoFitness novoObjetivo){
        if(novoObjetivo == null){
            throw new PlanoInvalidoException("Objetivo não pode ser nulo");
        }

        this.objetivoFitness = novoObjetivo;
    }
}