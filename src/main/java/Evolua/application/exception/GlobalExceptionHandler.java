package Evolua.application.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import Evolua.application.exception.dto.ErroResponse;
import Evolua.application.exception.usuario.EmailJaCadastradoException;
import Evolua.application.exception.usuario.UsuarioNaoEncontradoException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(
                    ex.getMessage(),
                    "USUARIO_NAO_ENCONTRADO",
                    LocalDateTime.now()
                ));
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ErroResponse> handleEmailJaCadastrado(EmailJaCadastradoException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErroResponse(
                    ex.getMessage(),
                    "EMAIL_JA_CADASTRADO",
                    LocalDateTime.now()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handleValidacao(MethodArgumentNotValidException ex){

        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse(
                    mensagem,
                    "VALIDACAO_INVALIDA",
                    LocalDateTime.now()
                ));
    }
}
