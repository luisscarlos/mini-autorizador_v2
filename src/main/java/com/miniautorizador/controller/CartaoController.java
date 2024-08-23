package com.miniautorizador.controller;

import com.miniautorizador.contract.CartaoContract;
import com.miniautorizador.dto.entrada.CriarCartao;
import com.miniautorizador.dto.retorno.CartaoResponse;
import com.miniautorizador.service.CartaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Log4j2
@RestController
@RequiredArgsConstructor
public class CartaoController implements CartaoContract {

    private final CartaoService cartaoService;

    @Override
    public ResponseEntity<CartaoResponse> criarCartao(CriarCartao cartao) {
        log.info("Iniciando criação de um novo cartão.");
        return new ResponseEntity<>(cartaoService.criarCartao(cartao), CREATED);
    }

    @Override
    public ResponseEntity<BigDecimal> obterSaldoCartao(String numeroCartao) {
        log.info("Iniciando busca de saldo do cartão.");
        return new ResponseEntity<>(cartaoService.obterSaldoCartao(numeroCartao), OK);
    }
}
