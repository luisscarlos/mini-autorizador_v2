package com.miniautorizador.schema;

import com.miniautorizador.model.Cartao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartaoResponse {

    @Schema(description = "Senha do cartão")
    private String senha;

    @Schema(description = "Número do cartão")
    private String numeroCartao;

    public CartaoResponse(Cartao cartao) {
        this.senha = cartao.getSenha();
        this.numeroCartao = cartao.getNumeroCartao();
    }

    public CartaoResponse(String dadosCartao) {
        this.numeroCartao = dadosCartao.substring(0, 16);
        this.senha = dadosCartao.substring(16);
    }
}
