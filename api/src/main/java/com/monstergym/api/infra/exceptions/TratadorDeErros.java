package com.monstergym.api.infra.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity especialidade(){

        return ResponseEntity.ok().body("A especialidade deve estar em letras maiúsculas.");
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity duplicatas(){

        return ResponseEntity.badRequest().body("Médico já cadastrado.");
    }

    private record DadosExecaoMedico(String atributo, String descricao){

        private DadosExecaoMedico(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
