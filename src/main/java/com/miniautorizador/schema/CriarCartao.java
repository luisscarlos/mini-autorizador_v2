package com.miniautorizador.schema;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CriarCartao {
//TODO aceitar no máximo 16 caracteres
    @NotNull
    @Size(min = 16, message = "O cartão deve ter 16 números")
    @Size(max = 16, message = "O cartão deve ter 16 números")
    @Schema(description = "Número do cartão", example = "6549873025634501", required = true)
    private String numeroCartao;

    @NotNull
    @Schema(description = "Senha do cartão", example = "1234", required = true)
    private String senha;

}
