package com.miniautorizador.controller;

import com.miniautorizador.contract.TransacoesContract;
import com.miniautorizador.enumerated.StatusTransacoesEnum;
import com.miniautorizador.exception.CartaoInexistenteException;
import com.miniautorizador.exception.SaldoInsuficienteException;
import com.miniautorizador.exception.SenhaInvalidaException;
import com.miniautorizador.schema.Transacao;
import com.miniautorizador.service.TransacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
public class TransacoesController implements TransacoesContract {

    @Autowired
    private TransacoesService transacoesService;

    @Override
    public ResponseEntity<StatusTransacoesEnum> realizarTransacao(Transacao transacao) {

        try {
            transacoesService.realizarTransacao(transacao);
            return new ResponseEntity<>(StatusTransacoesEnum.OK, OK);

        } catch (CartaoInexistenteException e) {
            return new ResponseEntity<>(StatusTransacoesEnum.CARTAO_INEXISTENTE, UNPROCESSABLE_ENTITY);

        } catch (SenhaInvalidaException e) {
            return new ResponseEntity<>(StatusTransacoesEnum.SENHA_INVALIDA, UNPROCESSABLE_ENTITY);

        } catch (SaldoInsuficienteException e) {
            return new ResponseEntity<>(StatusTransacoesEnum.SALDO_INSUFICIENTE, UNPROCESSABLE_ENTITY);
        }
    }
}
