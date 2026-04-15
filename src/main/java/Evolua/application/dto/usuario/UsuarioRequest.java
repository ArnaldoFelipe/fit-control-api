package Evolua.application.dto.usuario;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record UsuarioRequest(

    @NotBlank(message = "nome é obrigatorio")
    String nome,

    @NotBlank(message = "senha é obrigatorio")
    String senha,

    @NotBlank(message = "email é obrigatorio")
    @Email(message = "email invalido")
    String email,

    @Positive
    BigDecimal peso,

    @Positive
    BigDecimal altura,

    LocalDate dataNascimento
){}
