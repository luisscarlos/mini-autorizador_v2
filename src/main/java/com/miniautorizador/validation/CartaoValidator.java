package com.miniautorizador.validation;

import com.miniautorizador.dto.entrada.CriarCartao;

public interface CartaoValidator {

    void setProximoValidador(CartaoValidator proximo);

    void validar(CriarCartao cartao);
}

