package com.miniautorizador.controller;

import com.miniautorizador.contract.TransacoesContract;
import com.miniautorizador.dto.entrada.Transacao;
import com.miniautorizador.enumerated.StatusTransacoesEnum;
import com.miniautorizador.service.TransacoesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@Log4j2
@RestController
@RequiredArgsConstructor
public class TransacoesController implements TransacoesContract {

    private final TransacoesService transacoesService;

    @Override
    public ResponseEntity<StatusTransacoesEnum> realizarTransacao(Transacao transacao) {
        log.info("Iniciando Transação...");
        transacoesService.realizarTransacao(transacao);
        return new ResponseEntity<>(StatusTransacoesEnum.OK, OK);
    }
}
