package com.orion.mdd.controller.advice;

import com.orion.mdd.dto.FieldErrorDto;
import com.orion.mdd.exception.FieldsWithValueAlreadyTakenException;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        var fieldErrors = new ArrayList<FieldErrorDto>();
        ex.getBindingResult().getFieldErrors().forEach(fe ->
                fieldErrors.add(new FieldErrorDto(fe.getField(), fe.getDefaultMessage())));

        return new ResponseEntity<>(fieldErrors, ex.getStatusCode());
    }

    @ExceptionHandler(FieldsWithValueAlreadyTakenException.class)
    public ResponseEntity<List<FieldErrorDto>> handleFieldsWithValueAlreadyTakenException(FieldsWithValueAlreadyTakenException ex) {
        var fieldErrors = new ArrayList<FieldErrorDto>();
        ex.getFieldNames().forEach(fn -> fieldErrors.add(new FieldErrorDto(fn, "déjà utilisé")));

        return new ResponseEntity<>(fieldErrors, HttpStatus.BAD_REQUEST);
    }
}