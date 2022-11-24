package com.miniautorizador.schema;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Transacao {

    @Schema(description = "Número do cartão", example = "6549873025634501")
    private String numeroCartao;

    @Schema(description = "Senha do cartão", example = "1234")
    private String senhaCartao;

    @Schema(description = "Valor da transação", example = "113.34")
    private BigDecimal valor;
}
