package br.dev.ltres.cookin_up_api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> criaBodyComErro(@NonNull HttpStatus status, String error, String message) {
        return Map.of(
                "codigo", status.value(),
                "erro", error,
                "mensagem", message);
    }

    private ResponseEntity<Map<String, Object>> criaRespostaErroPadrao(Exception ex, @NonNull HttpStatus status,
            @NonNull String erro) {
        return ResponseEntity
                .status(status)
                .body(criaBodyComErro(status, erro, ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(EntityNotFoundException ex) {
        return criaRespostaErroPadrao(ex, HttpStatus.NOT_FOUND, "Recurso não encontrado");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(criaBodyComErro(HttpStatus.BAD_REQUEST, "Erro de validação", String.join("; ", erros)));
    }

}
