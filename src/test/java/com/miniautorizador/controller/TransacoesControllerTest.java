package com.miniautorizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniautorizador.schema.Transacao;
import com.miniautorizador.service.TransacoesService;
import com.miniautorizador.util.TransacaoBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TransacoesController.class)
@AutoConfigureMockMvc
class TransacoesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransacoesService transacoesService;

    private final Transacao novaTransacaoPadrao = TransacaoBuilder.novaTransacaoPadrao();

    private final static String BASE_URL = "/transacoes";

    private final static String APPLICATION_JSON = "application/json";

    @Test
    void quandoTransacaoValidaRetornaEnumOK() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaTransacaoPadrao)))
                .andExpect(status().is2xxSuccessful());
    }
}
