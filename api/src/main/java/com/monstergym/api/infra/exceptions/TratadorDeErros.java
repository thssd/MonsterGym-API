package com.monstergym.api.infra.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratar404(){

        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratar400(MethodArgumentNotValidException ex){
        var errors = ex.getFieldErrors();

        return ResponseEntity.badRequest().body(errors.stream().map(DadosExecaoMedico::new));
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity validar(ValidacaoException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    private record DadosExecaoMedico(String atributo, String descricao){

        private DadosExecaoMedico(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
