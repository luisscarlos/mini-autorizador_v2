package com.miniautorizador.service;

import com.miniautorizador.exception.CartaoInexistenteException;
import com.miniautorizador.exception.SaldoInsuficienteException;
import com.miniautorizador.exception.SenhaInvalidaException;
import com.miniautorizador.model.Cartao;
import com.miniautorizador.repository.CartaoRepository;
import com.miniautorizador.schema.Transacao;
import com.miniautorizador.util.CartaoBuilder;
import com.miniautorizador.util.TransacaoBuilder;
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

    private final Cartao cartaoPadraoEntidade = CartaoBuilder.cartaoPadraoEntidade();

    private final Cartao cartaoPadraoSaldoInsuficienteEntidade = CartaoBuilder.cartaoPadraoSaldoInsuficienteEntidade();

    private final Transacao novaTransacaoSenhaIncorreta = TransacaoBuilder.novaTransacaoSenhaIncorreta();

    private final Transacao novaTransacaoPadrao = TransacaoBuilder.novaTransacaoPadrao();


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
    void quandoCartaoNaoExistisThrowCartaoInexistenteException() {
        when(cartaoRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.empty());
        assertThrows(CartaoInexistenteException.class,
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
