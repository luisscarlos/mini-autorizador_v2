package com.miniautorizador.exception.handler;

import com.miniautorizador.enumerated.CartaoErrosEnum;
import com.miniautorizador.enumerated.StatusTransacoesEnum;
import com.miniautorizador.exception.CartaoInexistenteTransacaoException;
import com.miniautorizador.exception.SaldoInsuficienteException;
import com.miniautorizador.exception.SenhaInvalidaException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Log4j2
@ControllerAdvice
public class TransacoesExceptionHandler {

    @ExceptionHandler(CartaoInexistenteTransacaoException.class)
    public ResponseEntity<CartaoErrosEnum> handleCartaoInexistenteTransacaoException(CartaoInexistenteTransacaoException e) {
        log.error("ERRO: Cartão inexistente.");
        return new ResponseEntity<>(CartaoErrosEnum.CARTAO_INEXISTENTE, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<StatusTransacoesEnum> handleSenhaInvalidaException(SenhaInvalidaException e) {
        log.error("ERRO: Senha inválida.");
        return new ResponseEntity<>(StatusTransacoesEnum.SENHA_INVALIDA, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<StatusTransacoesEnum> handleSaldoInsuficienteException(SaldoInsuficienteException e) {
        log.error("ERRO: Saldo insuficiente.");
        return new ResponseEntity<>(StatusTransacoesEnum.SALDO_INSUFICIENTE, UNPROCESSABLE_ENTITY);
    }
}