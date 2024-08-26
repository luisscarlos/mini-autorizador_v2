package com.miniautorizador.validation.validators;

import com.miniautorizador.dto.entrada.CriarCartao;
import com.miniautorizador.exception.CartaoInvalidoException;
import com.miniautorizador.validation.AbstractCartaoValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class CartaoAlfanumericoValidator extends AbstractCartaoValidator {

    @Override
    protected void executarValidacao(CriarCartao cartao) {
        try {
            Long.parseLong(cartao.getNumeroCartao());
        } catch (NumberFormatException e) {
            log.error("O cartão {} deve conter apenas números.", cartao.getNumeroCartao());
            throw new CartaoInvalidoException();
        }

    }
}
