package com.miniautorizador.schema;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CriarCartao {
//TODO aceitar no máximo 16 caracteres
    @NotNull
//    @Length(max = 16)
    @Schema(description = "Número do cartão", example = "6549873025634501", required = true)
    private String numeroCartao;

    @NotNull
    @Schema(description = "Senha do cartão", example = "1234", required = true)
    private String senha;

}
