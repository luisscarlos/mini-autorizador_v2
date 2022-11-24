package com.miniautorizador.exception;

public class CartaoInvalidoException extends RuntimeException {

    public CartaoInvalidoException() {
        super("Cartão inválido.");
    }
}
