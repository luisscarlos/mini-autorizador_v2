package com.miniautorizador.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(requiredProperties = {"numeroCartao", "senha"})
public class CriarCartao {

    @NotNull(message = "O número do cartão não pode ser nulo")
    @Size(min = 16, message = "O cartão deve ter 16 números")
    @Size(max = 16, message = "O cartão deve ter 16 números")
    @Schema(description = "Número do cartão", example = "6549873025634501")
    private String numeroCartao;

    @NotNull(message = "A senha do cartão não pode ser nula")
    @NotBlank(message = "A senha do cartão não pode ser vazia")
    @Schema(description = "Senha do cartão", example = "1234")
    private String senha;

}
