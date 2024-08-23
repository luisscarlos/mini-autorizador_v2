package com.miniautorizador.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@Schema(requiredProperties = {"numeroCartao", "senhaCartao"})
public class Transacao {

    @NotNull
    @Size(min = 16, message = "O cartão deve ter 16 números")
    @Size(max = 16, message = "O cartão deve ter 16 números")
    @Schema(description = "Número do cartão", example = "6549873025634501")
    private String numeroCartao;

    @NotNull
    @NotBlank
    @Schema(description = "Senha do cartão", example = "1234")
    private String senhaCartao;

    @NotNull
    @Schema(description = "Valor da transação", example = "113.34")
    private BigDecimal valor;
}
