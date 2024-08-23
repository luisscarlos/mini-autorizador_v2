package com.miniautorizador.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miniautorizador.dto.entrada.Transacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransacaoBuilder {

    private final JsonConverter jsonConverter;

    public Transacao novaTransacaoPadrao() throws JsonProcessingException {
        String json = """
        {
            "numeroCartao": "1149873445634233",
            "senhaCartao": "1234",
            "valor": 500
        }
        """;
        return jsonConverter.jsonParaObjetoJava(json, Transacao.class);
    }

    public Transacao novaTransacaoSenhaIncorreta() throws JsonProcessingException {
        String json = """
        {
            "numeroCartao": "1149873445634233",
            "senhaCartao": "5555",
            "valor": 500
        }
        """;
        return jsonConverter.jsonParaObjetoJava(json, Transacao.class);
    }

    public Transacao novaTransacaoCartaoInexistente() throws JsonProcessingException {
        String json = """
        {
            "numeroCartao": "4444444444444444",
            "senhaCartao": "4444",
            "valor": 54.33
        }
        """;
        return jsonConverter.jsonParaObjetoJava(json, Transacao.class);
    }

    public Transacao novaTransacaoSaldoInsuficiente() throws JsonProcessingException {
        String json = """
        {
            "numeroCartao": "1149873445634233",
            "senhaCartao": "1234",
            "valor": 20.34
        }
        """;
        return jsonConverter.jsonParaObjetoJava(json, Transacao.class);
    }
}
