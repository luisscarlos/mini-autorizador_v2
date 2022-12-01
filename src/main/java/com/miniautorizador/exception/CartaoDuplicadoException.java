package com.miniautorizador.exception;

import com.miniautorizador.schema.CriarCartao;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
@Getter
public class CartaoDuplicadoException extends RuntimeException {

    private final transient CriarCartao cartao;

    public CartaoDuplicadoException (CriarCartao cartao) {
        super();
        this.cartao = cartao;
    }

}
