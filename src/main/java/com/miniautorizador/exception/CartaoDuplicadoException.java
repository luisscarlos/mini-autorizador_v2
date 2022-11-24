package com.miniautorizador.exception;

public class CartaoDuplicadoException extends RuntimeException {

    public CartaoDuplicadoException (String numeroCartao, String senha) {
        super(numeroCartao + senha);
    }
}
