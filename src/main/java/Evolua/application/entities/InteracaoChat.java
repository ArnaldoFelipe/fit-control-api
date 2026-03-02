package Evolua.application.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "interacao_chat")
public class InteracaoChat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @Column(columnDefinition = "Text")
    private String menssagemUsuario;

    @Column(columnDefinition = "Text")
    private String respostaIA;

    private LocalDateTime dataCriacao;

    @PrePersist
    public void PrePersist(){
        this.dataCriacao = LocalDateTime.now();
    }

    public InteracaoChat(){}

    public InteracaoChat(Usuario usuario, String menssagemUsuario, String respostaIA) {
        this.usuario = usuario;
        this.menssagemUsuario = menssagemUsuario;
        this.respostaIA = respostaIA;
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

    public String getMenssagemUsuario() {
        return menssagemUsuario;
    }

    public void setMenssagemUsuario(String menssagemUsuario) {
        this.menssagemUsuario = menssagemUsuario;
    }

    public String getRespostaIA() {
        return respostaIA;
    }

    public void setRespostaIA(String respostaIA) {
        this.respostaIA = respostaIA;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}