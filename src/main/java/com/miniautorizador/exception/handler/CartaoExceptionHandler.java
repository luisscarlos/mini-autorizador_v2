package com.miniautorizador.exception.handler;

import com.miniautorizador.dto.retorno.CartaoResponse;
import com.miniautorizador.enumerated.CartaoErrosEnum;
import com.miniautorizador.exception.CartaoDuplicadoException;
import com.miniautorizador.exception.CartaoInexistenteSaldoException;
import com.miniautorizador.exception.CartaoInvalidoException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Log4j2
@ControllerAdvice
public class CartaoExceptionHandler {

    @ExceptionHandler(CartaoDuplicadoException.class)
    public ResponseEntity<CartaoResponse> handleCartaoDuplicadoException(CartaoDuplicadoException e) {
        log.error("ERRO: Cartão duplicado. Número do Cartão: {}", e.getNumeroCartao());
        CartaoResponse cartaoResponse = new CartaoResponse(e.getSenha(), e.getNumeroCartao());
        return new ResponseEntity<>(cartaoResponse, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(CartaoInvalidoException.class)
    public ResponseEntity<CartaoErrosEnum> handleCartaoInvalidoException(CartaoInvalidoException e) {
        log.error("ERRO: Cartão inválido.");
        return new ResponseEntity<>(CartaoErrosEnum.CARTAO_INVALIDO, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(CartaoInexistenteSaldoException.class)
    public ResponseEntity<String> handleCartaoInexistenteSaldoException(CartaoInexistenteSaldoException e) {
        log.error("ERRO: Cartão inexistente.");
        return new ResponseEntity<>(null, NOT_FOUND);
    }
}