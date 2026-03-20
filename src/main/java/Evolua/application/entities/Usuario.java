package Evolua.application.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import Evolua.application.entities.enums.ClassificacaoImc;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;


@Entity 
@Table(name = "usuario")
public class Usuario implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @PrePersist
    public void PrePersist(){
        this.dataCriacao = LocalDateTime.now();
    }

    @Column(name = "peso", precision = 5, scale = 2)
    private BigDecimal peso;

    @Column(name = "altura", precision = 4, scale = 2)
    private BigDecimal altura;

    public Usuario(){}

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public BigDecimal getPeso(){
        return peso;
    }

    public void setPeso(BigDecimal peso){
        if(peso != null && peso.compareTo(BigDecimal.ZERO) < 0){
        throw new IllegalArgumentException("Peso não pode ser negativo");
    }
        this.peso = peso;
    }

    public BigDecimal getAltura(){
        return altura;
    }

    public void setAltura(BigDecimal altura){
        if(altura != null && altura.compareTo(BigDecimal.ZERO) < 0){
        throw new IllegalArgumentException("Altura não pode ser negativo");
    }
        this.altura = altura;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    public BigDecimal getImc(){
        if (peso == null || altura == null) return null;

        return peso.divide(
            altura.multiply(altura),
            2,
            java.math.RoundingMode.HALF_UP
        );
    }

    public ClassificacaoImc getClassificacaoImc(){
        BigDecimal imc = getImc();

        if (imc == null) return null;

        if (imc.compareTo(new BigDecimal("18.5")) < 0)
            return ClassificacaoImc.MAGREZA;

        if (imc.compareTo(new BigDecimal("25")) < 0)
            return ClassificacaoImc.NORMAL;

        if (imc.compareTo(new BigDecimal("30")) < 0)
            return ClassificacaoImc.SOBREPESO;

        return ClassificacaoImc.OBESIDADE;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public void setPassword(String password) {
        this.senha = password;
    }

}
