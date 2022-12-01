package com.miniautorizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniautorizador.exception.CartaoDuplicadoException;
import com.miniautorizador.exception.CartaoInexistenteSaldoException;
import com.miniautorizador.exception.CartaoInvalidoException;
import com.miniautorizador.schema.CriarCartao;
import com.miniautorizador.service.CartaoService;
import com.miniautorizador.util.CartaoBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

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

    private final CriarCartao cartaoPadraoDuplicado = CartaoBuilder.cartaoPadraoDuplicado();

    private final CriarCartao novoCartaoCorreto = CartaoBuilder.novoCartaoCorreto();

    private final CriarCartao novoCartaoComAlfaNumerico = CartaoBuilder.novoCartaoComAlfaNumerico();

    private final static String BASE_URL = "/cartoes";

    private final static String APPLICATION_JSON = "application/json";

    private final static String NUMERO_CARTAO_VALIDO = "1149873445634233";

    private final static String NUMERO_CARTAO_INEXISTENTE = "3333333333333333";

    @Test
    void quandoCartaoCriadoComSucessoRetornaHttpStatusCode201() throws Exception {
        mockMvc.perform(post(BASE_URL)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoCartaoCorreto)))
                .andExpect(status().isCreated());
    }

    @Test
    void quandoCartaoDuplicadoRetornaHttpStatusCode422() throws Exception {
        when(cartaoService.criarCartao(cartaoPadraoDuplicado)).thenThrow(CartaoDuplicadoException.class);

        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartaoPadraoDuplicado)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void quandoCartaoCriadoComAlfanumericoRetornaHttpStatusCode422() throws Exception {
        when(cartaoService.criarCartao(novoCartaoComAlfaNumerico)).thenThrow(CartaoInvalidoException.class);

        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoCartaoComAlfaNumerico)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void quandoCartaoValidoRetornaSaldoCartaoRetornaHttpStatusCode200() throws Exception {
        when(cartaoService.obterSaldoCartao(NUMERO_CARTAO_VALIDO)).thenReturn(BigDecimal.valueOf(500));

        mockMvc.perform(get(BASE_URL + "/{numeroCartao}", NUMERO_CARTAO_VALIDO)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void quandoCartaoInexistenteAoObterSaldoRetornaHttpStatus404() throws Exception {
        when(cartaoService.obterSaldoCartao(NUMERO_CARTAO_INEXISTENTE)).thenThrow(CartaoInexistenteSaldoException.class);

        mockMvc.perform(get(BASE_URL + "/{numeroCartao}", NUMERO_CARTAO_INEXISTENTE)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
