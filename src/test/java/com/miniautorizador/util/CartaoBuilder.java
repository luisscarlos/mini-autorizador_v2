package com.miniautorizador.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miniautorizador.domain.Cartao;
import com.miniautorizador.dto.entrada.CriarCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CartaoBuilder {

    private final JsonConverter jsonConverter;

    public Cartao cartaoPadraoEntidade() throws JsonProcessingException {
    String json = """
    {
        "id": "%s",
        "numeroCartao": "1149873445634233",
        "senha": "1234",
        "saldo": 500
    }
    """.formatted(UUID.randomUUID().toString());
    return jsonConverter.jsonParaObjetoJava(json, Cartao.class);
}

public CriarCartao cartaoPadraoDuplicado() throws JsonProcessingException {
    String json = """
    {
        "numeroCartao": "1149873445634233",
        "senha": "1234"
    }
    """;
    return jsonConverter.jsonParaObjetoJava(json, CriarCartao.class);
}

public Cartao cartaoPadraoSaldoInsuficienteEntidade() throws JsonProcessingException {
    String json = """
    {
        "id": "%s",
        "numeroCartao": "1149873445634233",
        "senha": "1234",
        "saldo": 20.15
    }
    """.formatted(UUID.randomUUID().toString());
    return jsonConverter.jsonParaObjetoJava(json, Cartao.class);
}

public CriarCartao novoCartaoCorreto() throws JsonProcessingException {
    String json = """
    {
        "numeroCartao": "6549873885634223",
        "senha": "1234"
    }
    """;
    return jsonConverter.jsonParaObjetoJava(json, CriarCartao.class);
}

public CriarCartao novoCartaoComAlfaNumerico() throws JsonProcessingException {
    String json = """
    {
        "numeroCartao": "dd49873xx56342vv",
        "senha": "1234"
    }
    """;
    return jsonConverter.jsonParaObjetoJava(json, CriarCartao.class);
}

}
