package com.miniautorizador.service;

import com.miniautorizador.domain.Cartao;
import com.miniautorizador.dto.entrada.CriarCartao;
import com.miniautorizador.dto.retorno.CartaoResponse;
import com.miniautorizador.exception.CartaoDuplicadoException;
import com.miniautorizador.exception.CartaoInexistenteSaldoException;
import com.miniautorizador.exception.CartaoInvalidoException;
import com.miniautorizador.repository.CartaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class CartaoService {

    private final CartaoRepository cartaoRepository;

    public CartaoResponse criarCartao(CriarCartao cartao) {
        log.info("Criando um novo cartão.");

        verificaSeCartaoTemApenasNumeros(cartao.getNumeroCartao());

        verificaSeCartaoDuplicado(cartao);

        Cartao novoCartao = new Cartao(cartao);
        cartaoRepository.save(novoCartao);

        log.info("Cartão {} criado com sucesso.", cartao.getNumeroCartao());
        return new CartaoResponse(novoCartao);
    }

    public BigDecimal obterSaldoCartao(String numeroCartao) {
        log.info("Saldo do Cartão");

        return cartaoRepository.findByNumeroCartao(numeroCartao)
                .map(Cartao::getSaldo).orElseThrow(CartaoInexistenteSaldoException::new);
    }

    private void verificaSeCartaoDuplicado(CriarCartao cartao) {
        cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao())
                .ifPresent(c -> {
                    log.error("O cartão {} já existe.", cartao.getNumeroCartao());
                    throw new CartaoDuplicadoException(cartao.getNumeroCartao(), cartao.getSenha());
                });
    }

    private void verificaSeCartaoTemApenasNumeros(String numeroCartao) {
        if(!numeroCartao.matches("^\\d+$"))
            throw new CartaoInvalidoException();
    }
}
