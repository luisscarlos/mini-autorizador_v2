package com.miniautorizador.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miniautorizador.domain.Cartao;
import com.miniautorizador.dto.entrada.Transacao;
import com.miniautorizador.exception.CartaoInexistenteTransacaoException;
import com.miniautorizador.exception.SaldoInsuficienteException;
import com.miniautorizador.exception.SenhaInvalidaException;
import com.miniautorizador.repository.CartaoRepository;
import com.miniautorizador.util.CartaoBuilder;
import com.miniautorizador.util.JsonConverter;
import com.miniautorizador.util.TransacaoBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TransacoesServiceTest {

    @InjectMocks
    private TransacoesService transacoesService;
    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private JsonConverter jsonConverter;

    private Cartao cartaoPadraoEntidade;

    private Cartao cartaoPadraoSaldoInsuficienteEntidade;

    private Transacao novaTransacaoSenhaIncorreta;

    private Transacao novaTransacaoPadrao;

    @BeforeEach
    void setup() throws JsonProcessingException {
        CartaoBuilder cartaoBuilder = new CartaoBuilder(jsonConverter);
        TransacaoBuilder transacaoBuilder = new TransacaoBuilder(jsonConverter);
        cartaoPadraoEntidade = cartaoBuilder.cartaoPadraoEntidade();
        cartaoPadraoSaldoInsuficienteEntidade = cartaoBuilder.cartaoPadraoSaldoInsuficienteEntidade();
        novaTransacaoSenhaIncorreta = transacaoBuilder.novaTransacaoSenhaIncorreta();
        novaTransacaoPadrao = transacaoBuilder.novaTransacaoPadrao();
    }


    @Test
    void quandoSenhaIncorretaThrowsSenhaIncorretaException() {
        when(cartaoRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.ofNullable(cartaoPadraoEntidade));
        assertThrows(SenhaInvalidaException.class,
                () -> transacoesService.realizarTransacao(novaTransacaoSenhaIncorreta));
    }

    @Test
    void quandoCartaoSemSaldoThrowsSaldoInsuficienteException() {
        when(cartaoRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.ofNullable(cartaoPadraoSaldoInsuficienteEntidade));
        assertThrows(SaldoInsuficienteException.class,
                () -> transacoesService.realizarTransacao(novaTransacaoPadrao));
    }

    @Test
    void quandoCartaoNaoExisteThrowsCartaoInexistenteException() {
        when(cartaoRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.empty());
        assertThrows(CartaoInexistenteTransacaoException.class,
                () -> transacoesService.realizarTransacao(novaTransacaoPadrao));
    }

    @Test
    void quandoTransacaoValidaEfetuarTransacao() {
        when(cartaoRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.ofNullable(cartaoPadraoEntidade));

        assertNotNull(cartaoPadraoEntidade);
        transacoesService.realizarTransacao(novaTransacaoPadrao);

        verify(cartaoRepository).save(cartaoPadraoEntidade);
    }
}
