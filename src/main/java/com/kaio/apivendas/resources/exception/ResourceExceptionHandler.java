package com.kaio.apivendas.resources.exception;

import com.kaio.apivendas.services.exceptions.DataIntegrityException;
import com.kaio.apivendas.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
Marca a classe como um manipulador Global de exceções para
os controladores(Resources)

Marca o método ObjectNotFound para lidar com exceções do tipo
ObjectNotFoundException

StandardError é a classe personalizada para representar erros padrões
na resposta da API, Mostrando Status HTTP, Menssagem e DATA/HORA
e retorna um ResponseEntity com status code e body encapsulado no err.
*/


@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException error, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), error.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException error, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), error.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
