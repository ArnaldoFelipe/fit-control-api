package Evolua.application.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import Evolua.application.exception.dto.ErroResponse;
import Evolua.application.exception.exercicio.ExercicioNaoEncontradoException;
import Evolua.application.exception.planoTreino.DiaTreinoNaoEncontradoException;
import Evolua.application.exception.planoTreino.DiasDuplicadosException;
import Evolua.application.exception.planoTreino.PlanoInvalidoException;
import Evolua.application.exception.planoTreino.PlanoTreinoNaoEncontradoException;
import Evolua.application.exception.treino.TreinoDuplicadoException;
import Evolua.application.exception.treino.TreinoInvalidoException;
import Evolua.application.exception.treino.TreinoNaoEncontradoException;
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

    @ExceptionHandler(PlanoTreinoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> handlePlanoTreinoNaoEncontrado(PlanoTreinoNaoEncontradoException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(
                    ex.getMessage(),
                    "USUARIO_NAO_POSSUI_PLANO_ATIVO",
                    LocalDateTime.now()
                ));
    }

    @ExceptionHandler(DiasDuplicadosException.class)
    public ResponseEntity<ErroResponse> handleDiasDuplicados(DiasDuplicadosException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErroResponse(
                    ex.getMessage(),
                    "DIAS_NAO_PODEM_SER_DUPLICADOS",
                    LocalDateTime.now()
                ));
    }

    @ExceptionHandler(PlanoInvalidoException.class)
    public ResponseEntity<ErroResponse> handlePlanoInvalido(PlanoInvalidoException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse(
                    ex.getMessage(),
                    "PLANO_DEVE_CONTER_DIAS",
                    LocalDateTime.now()
                ));
    }

    @ExceptionHandler(ExercicioNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> handleExercicioNaoEncontrado(ExercicioNaoEncontradoException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(
                    ex.getMessage(),
                    "EXERCICIO_NAO_ENCONTRADO",
                    LocalDateTime.now()
                ));
    }

    @ExceptionHandler(TreinoInvalidoException.class)
    public ResponseEntity<ErroResponse> handleTreinoInvalido(TreinoInvalidoException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse(
                    ex.getMessage(),
                    "TREINO_INVALIDO",
                    LocalDateTime.now()
                ));
    }

    @ExceptionHandler(TreinoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> handleTreinoNaoEncontrado(TreinoNaoEncontradoException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(
                    ex.getMessage(),
                    "TREINO_NAO_ENCONTRADO",
                    LocalDateTime.now()
                ));
    }

    @ExceptionHandler(TreinoDuplicadoException.class)
    public ResponseEntity<ErroResponse> handleTreinoDuplicado(TreinoDuplicadoException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErroResponse(
                    ex.getMessage(),
                    "EXERCICIO_DUPLICADO",
                    LocalDateTime.now()
                ));
    }

    @ExceptionHandler(DiaTreinoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> handleDiaTreinoNaoEncontrado(DiaTreinoNaoEncontradoException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(
                    ex.getMessage(),
                    "DIA_NAO_ENCONTRADO",
                    LocalDateTime.now()
                ));
    }
}
