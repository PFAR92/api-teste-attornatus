package com.attornatus.api.domain.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<Exception.Field> fields = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String name = ((FieldError) error).getField();
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            fields.add(new Exception.Field(name, message));
        }

        Exception exception = new Exception();
        exception.setStatus(status.value());
        exception.setDateTime(LocalDateTime.now());
        exception.setTitle("Um ou mais campos estão inválidos. Faça o preenchimento correto");
        exception.setFields(fields);

        return handleExceptionInternal(ex, exception, headers, status, request);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleBusiness(CustomException ex, WebRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;

        Exception error = new Exception();
        error.setStatus(status.value());
        error.setDateTime(LocalDateTime.now());
        error.setTitle(ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }
}
