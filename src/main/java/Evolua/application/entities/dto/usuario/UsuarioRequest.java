package Evolua.application.entities.dto.usuario;

import Evolua.application.entities.enums.ObjetivoFitness;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequest(

    @NotBlank(message = "nome é obrigatorio")
    String nome,

    @NotBlank(message = "senha é obrigatorio")
    String senha,

    @NotBlank(message = "email é obrigatorio")
    @Email(message = "email invalido")
    String email,

    @NotNull(message = "objetivo é obrigatorio")
    ObjetivoFitness objetivo
){}
