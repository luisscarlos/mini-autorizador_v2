package com.miniautorizador.util;

import com.miniautorizador.schema.Transacao;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@NoArgsConstructor
public class TransacaoBuilder {

    public static Transacao novaTransacaoPadrao() {
        return Transacao.builder()
                .numeroCartao("1149873445634233")
                .senhaCartao("1234")
                .valor(BigDecimal.valueOf(54.33))
                .build();
    }

    public static Transacao novaTransacaoSenhaIncorreta() {
        return Transacao.builder()
                .numeroCartao("1149873445634233")
                .senhaCartao("5555")
                .valor(BigDecimal.valueOf(54.33))
                .build();
    }
}
