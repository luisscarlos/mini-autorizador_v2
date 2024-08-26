package com.miniautorizador.validation.validators;

import com.miniautorizador.dto.entrada.CriarCartao;
import com.miniautorizador.exception.CartaoDuplicadoException;
import com.miniautorizador.repository.CartaoRepository;
import com.miniautorizador.validation.AbstractCartaoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class CartaoDuplicadoValidator extends AbstractCartaoValidator {

    private final CartaoRepository cartaoRepository;

    @Override
    protected void executarValidacao(CriarCartao cartao) {
        cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao())
                .ifPresent(c -> {
                    log.error("O cartão {} já existe.", cartao.getNumeroCartao());
                    throw new CartaoDuplicadoException(cartao.getNumeroCartao(), cartao.getSenha());
                });
    }

}
