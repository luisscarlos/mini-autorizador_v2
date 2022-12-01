package com.miniautorizador.controller;

import com.miniautorizador.contract.CartaoContract;
import com.miniautorizador.exception.CartaoDuplicadoException;
import com.miniautorizador.exception.CartaoInexistenteSaldoException;
import com.miniautorizador.exception.CartaoInvalidoException;
import com.miniautorizador.schema.CartaoResponse;
import com.miniautorizador.schema.CriarCartao;
import com.miniautorizador.service.CartaoService;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.*;

@Log4j2
@NoArgsConstructor
@RestController
public class CartaoController implements CartaoContract {

    @Autowired
    private CartaoService cartaoService;

    @Override
    public ResponseEntity<CartaoResponse> criarCartao(CriarCartao cartao) {
        CartaoResponse response;
        try {
            response = cartaoService.criarCartao(cartao);
            return new ResponseEntity<>(response, CREATED);

        } catch (CartaoDuplicadoException e) {
            response = new CartaoResponse(e.getNumeroCartao(), e.getSenha());
            log.error("ERRO: Cartão duplicado.");
            return new ResponseEntity<>(response, UNPROCESSABLE_ENTITY);

        } catch (CartaoInvalidoException e) {
            log.error("ERRO: Cartão inválido.");
            return new ResponseEntity<>(null, UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public ResponseEntity<BigDecimal> obterSaldoCartao(String numeroCartao) {
        try {
            BigDecimal response = cartaoService.obterSaldoCartao(numeroCartao);
            return new ResponseEntity<>(response, OK);

        } catch (CartaoInexistenteSaldoException e) {
            log.error("ERRO: Cartão inexistente.");
            return new ResponseEntity<>(null, NOT_FOUND);
        }
    }
}
