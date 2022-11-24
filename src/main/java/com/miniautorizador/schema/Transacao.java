package com.miniautorizador.schema;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Transacao {

    private String numeroCartao;

    private String senhaCartao;

    private BigDecimal valor;
}
