package com.miniautorizador.controller;

import com.miniautorizador.contract.TransacoesContract;
import com.miniautorizador.dto.entrada.Transacao;
import com.miniautorizador.enumerated.StatusTransacoesEnum;
import com.miniautorizador.exception.CartaoInexistenteTransacaoException;
import com.miniautorizador.exception.SaldoInsuficienteException;
import com.miniautorizador.exception.SenhaInvalidaException;
import com.miniautorizador.service.TransacoesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Log4j2
@RestController
@RequiredArgsConstructor
public class TransacoesController implements TransacoesContract {

    private final TransacoesService transacoesService;

    @Override
    public ResponseEntity<StatusTransacoesEnum> realizarTransacao(Transacao transacao) {

        try {
            transacoesService.realizarTransacao(transacao);
            return new ResponseEntity<>(StatusTransacoesEnum.OK, OK);

        } catch (CartaoInexistenteTransacaoException e) {
            log.error("ERRO: Cartão inválido.");
            return new ResponseEntity<>(StatusTransacoesEnum.CARTAO_INEXISTENTE, UNPROCESSABLE_ENTITY);

        } catch (SenhaInvalidaException e) {
            log.error("ERRO: Senha inválida.");
            return new ResponseEntity<>(StatusTransacoesEnum.SENHA_INVALIDA, UNPROCESSABLE_ENTITY);

        } catch (SaldoInsuficienteException e) {
            log.error("ERRO: Saldo insuficiente.");
            return new ResponseEntity<>(StatusTransacoesEnum.SALDO_INSUFICIENTE, UNPROCESSABLE_ENTITY);
        }
    }
}
