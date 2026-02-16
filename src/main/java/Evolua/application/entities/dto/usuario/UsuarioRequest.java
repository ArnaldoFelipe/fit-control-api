package Evolua.application.entities.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequest(

    @NotBlank(message = "nome é obrigatorio")
    String nome,

    @NotBlank(message = "senha é obrigatorio")
    String senha,

    @NotBlank(message = "email é obrigatorio")
    @Email(message = "email invalido")
    String email
){}
