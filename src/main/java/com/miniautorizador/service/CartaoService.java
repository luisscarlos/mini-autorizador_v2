package com.miniautorizador.service;

import com.miniautorizador.exception.CartaoDuplicadoException;
import com.miniautorizador.exception.CartaoInexistenteException;
import com.miniautorizador.model.Cartao;
import com.miniautorizador.repository.CartaoRepository;
import com.miniautorizador.schema.CartaoResponse;
import com.miniautorizador.schema.CriarCartao;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@Log4j2
@Transactional
@NoArgsConstructor
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    public CartaoResponse criarCartao(CriarCartao cartao) {
        log.info("Criando um novo cartão.");

        verificaSeCartaoDuplicado(cartao);

        Cartao novoCartao = new Cartao(cartao);

        log.info("Cartão {} criado com sucesso.", cartao.getNumeroCartao());
        return new CartaoResponse(cartaoRepository.save(novoCartao));
    }

    public BigDecimal obterSaldoCartao(String numeroCartao) {
        log.info("Saldo do Cartão");

        return cartaoRepository.findByNumeroCartao(numeroCartao)
                .map(Cartao::getSaldo).orElseThrow(CartaoInexistenteException::new);
    }
    private void verificaSeCartaoDuplicado(CriarCartao cartao) {
        cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao())
                .ifPresent(c -> {
                    log.error("O cartão {} já existe.", cartao.getNumeroCartao());
                    throw new CartaoDuplicadoException(cartao.getNumeroCartao(), cartao.getSenha());
                });
    }

    //TODO bloquear letras na string
//    private Boolean verificaNumeroCartao(String numeroCartao) {
//
//        () -> numeroCartao.matches("^[0-9]+$").
//
////    VERIFICA SE NA STRING POSSUI APENAS NÚMEROS
//        return numeroCartao.matches("^[0-9]+$");
//    }
}
