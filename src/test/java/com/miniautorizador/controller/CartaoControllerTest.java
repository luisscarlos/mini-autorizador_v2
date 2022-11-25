package com.miniautorizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniautorizador.model.Cartao;
import com.miniautorizador.repository.CartaoRepository;
import com.miniautorizador.schema.CartaoResponse;
import com.miniautorizador.schema.CriarCartao;
import com.miniautorizador.service.CartaoService;
import com.miniautorizador.util.CartaoBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CartaoController.class)
@AutoConfigureMockMvc
class CartaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartaoService cartaoService;

    @Mock
    private CartaoRepository cartaoRepository;

    private final Cartao cartaoPadraoEntidade = CartaoBuilder.cartaoPadraoEntidade();

    private final CriarCartao novoCartaoCorreto = CartaoBuilder.novoCartaoCorreto();

    private final CartaoResponse cartaoResponse = CartaoBuilder.cartaoResponse();

    private final static String BASE_URL = "/cartoes";

    private final static String APPLICATION_JSON = "application/json";

    @Test
    void quandoCartaoCriadoComSucessoRetornaStatusCode2xx() throws Exception {
        mockMvc.perform(post(BASE_URL)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoCartaoCorreto)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void quandoCartaoDuplicadoRetornaStatusCode4xx() throws Exception {
        when(cartaoService.criarCartao(novoCartaoCorreto)).thenReturn(cartaoResponse);

        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoCartaoCorreto)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void quandoCartaoValidoRetornaSaldoCartao() throws Exception {
        when(cartaoRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.of(cartaoPadraoEntidade));

        mockMvc.perform(get(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .queryParam("numeroCartao", "1149873445634233"))
                .andExpect(status().is2xxSuccessful());
    }
}
