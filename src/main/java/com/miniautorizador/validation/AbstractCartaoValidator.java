package com.miniautorizador.validation;

import com.miniautorizador.dto.entrada.CriarCartao;

import java.util.Objects;

public abstract class AbstractCartaoValidator implements CartaoValidator {

        private CartaoValidator proximoValidador;

        @Override
        public void setProximoValidador(CartaoValidator proximoValidador) {
            this.proximoValidador = proximoValidador;
        }

        @Override
        public void validar(CriarCartao cartao) {
            executarValidacao(cartao);

            if(Objects.nonNull(proximoValidador)) {
                proximoValidador.validar(cartao);
            }
        }

    protected abstract void executarValidacao(CriarCartao cartao);
}
