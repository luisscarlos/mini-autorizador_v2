package com.miniautorizador.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miniautorizador.util.JsonConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final JsonConverter jsonConverter;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) throws JsonProcessingException {
        Map<String, String> errorMessages = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                ));
        log.error("ERRO: Violação de argumento - {}", errorMessages);
        return new ResponseEntity<>(jsonConverter.objetoJavaParaJson(errorMessages), BAD_REQUEST);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> handleJsonProcessingException(JsonProcessingException e) {
        String errorMessage = "Erro ao processar JSON: " + e.getOriginalMessage();
        log.error("ERRO: {}", errorMessage);
        return new ResponseEntity<>(errorMessage, BAD_REQUEST);
    }
}