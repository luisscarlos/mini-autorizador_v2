package com.miniautorizador.service;

import com.miniautorizador.domain.Cartao;
import com.miniautorizador.dto.entrada.Transacao;
import com.miniautorizador.exception.CartaoInexistenteTransacaoException;
import com.miniautorizador.exception.SaldoInsuficienteException;
import com.miniautorizador.exception.SenhaInvalidaException;
import com.miniautorizador.repository.CartaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class TransacoesService {

    private final CartaoRepository cartaoRepository;

    private final Lock lock = new ReentrantLock();

    public void realizarTransacao(Transacao transacao) {
        log.info("Executando Transação...");

        cartaoRepository.findByNumeroCartao(transacao.getNumeroCartao())
                .ifPresentOrElse(cartao -> {
                    log.info("Cartão localizado {}.", cartao.getNumeroCartao());

                    verificaSenhaCartao(transacao.getSenhaCartao(), cartao.getSenha());

                    verificaSaldoCartao(cartao.getSaldo(), transacao.getValor());

                    efetuaTransacao(cartao, transacao);

                }, () -> {
                    log.error("Cartão inexistente.");
                    throw new CartaoInexistenteTransacaoException();
                });
    }

    private void verificaSenhaCartao(String senhaTransacao, String senhaCartao) {
        if (!senhaTransacao.equals(senhaCartao))
            throw new SenhaInvalidaException();
        log.info("Senha correta.");
    }

    private void verificaSaldoCartao(BigDecimal saldoCartao, BigDecimal valorTransacao) {
        if ((saldoCartao.subtract(valorTransacao)).signum() == -1)
            throw new SaldoInsuficienteException();
        log.info("Saldo suficiente.");
    }

    private void efetuaTransacao(Cartao cartao, Transacao transacao) {
        boolean lockAcquired = lock.tryLock();

        try {
            if (lockAcquired) {
                cartao.setSaldo(cartao.getSaldo().subtract(transacao.getValor()));
                cartaoRepository.save(cartao);
                log.info("Transação efetuada.");
            }

        } finally {
            if (lockAcquired) lock.unlock();
        }
    }
}
