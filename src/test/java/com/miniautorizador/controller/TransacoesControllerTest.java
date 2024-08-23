package com.miniautorizador.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miniautorizador.config.SecurityConfig;
import com.miniautorizador.dto.entrada.Transacao;
import com.miniautorizador.exception.CartaoInexistenteTransacaoException;
import com.miniautorizador.exception.SaldoInsuficienteException;
import com.miniautorizador.exception.SenhaInvalidaException;
import com.miniautorizador.service.TransacoesService;
import com.miniautorizador.util.JsonConverter;
import com.miniautorizador.util.TransacaoBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;

@WebMvcTest(controllers = TransacoesController.class)
@ContextConfiguration(classes = { SecurityConfig.class, TransacoesController.class })
class TransacoesControllerTest extends BaseControllerTest {

    @MockBean
    private TransacoesService transacoesService;

    @InjectMocks
    private JsonConverter jsonConverter;

    private Transacao novaTransacaoPadrao;

    private Transacao novaTransacaoCartaoInexistente;

    private Transacao novaTransacaoSenhaIncorreta;

    private Transacao novaTransacaoSaldoInsuficiente;

    private static final String BASE_URL = "/transacoes";

    @BeforeEach
    void setup() throws JsonProcessingException {
        TransacaoBuilder transacaoBuilder = new TransacaoBuilder(jsonConverter);
        novaTransacaoPadrao = transacaoBuilder.novaTransacaoPadrao();
        novaTransacaoCartaoInexistente = transacaoBuilder.novaTransacaoCartaoInexistente();
        novaTransacaoSenhaIncorreta = transacaoBuilder.novaTransacaoSenhaIncorreta();
        novaTransacaoSaldoInsuficiente = transacaoBuilder.novaTransacaoSaldoInsuficiente();
    }

    @ParameterizedTest
    @CsvSource({
            "true, 200",
            "false, 401"
    })
    void quandoTransacaoValidaRetornaHttpStatus200(boolean isUsuarioAutenticado, int statusHttpEsperado) throws Exception {
        assertNotNull(novaTransacaoPadrao);
        performMockMvcRequestAndExpectHttpStatus(isUsuarioAutenticado,"POST", BASE_URL, novaTransacaoPadrao, statusHttpEsperado);
    }

    @ParameterizedTest
    @CsvSource({
            "true, 422",
            "false, 401"
    })
    void quandoTransacaoComCartaoInexistenteRetornaHttpStatus422(boolean isUsuarioAutenticado, int statusHttpEsperado) throws Exception {
        assertNotNull(novaTransacaoCartaoInexistente);
        doThrow(CartaoInexistenteTransacaoException.class).when(transacoesService).realizarTransacao(novaTransacaoCartaoInexistente);
        performMockMvcRequestAndExpectHttpStatus(isUsuarioAutenticado,"POST", BASE_URL, novaTransacaoCartaoInexistente, statusHttpEsperado);
    }

    @ParameterizedTest
    @CsvSource({
            "true, 422",
            "false, 401"
    })
    void quandoTransacaoComSenhaIncorretaRetornaHttpStatus422(boolean isUsuarioAutenticado, int statusHttpEsperado) throws Exception {
        assertNotNull(novaTransacaoSenhaIncorreta);
        doThrow(SenhaInvalidaException.class).when(transacoesService).realizarTransacao(novaTransacaoSenhaIncorreta);
        performMockMvcRequestAndExpectHttpStatus(isUsuarioAutenticado,"POST", BASE_URL, novaTransacaoSenhaIncorreta, statusHttpEsperado);
    }

    @ParameterizedTest
    @CsvSource({
            "true, 422",
            "false, 401"
    })
    void quandoTransacaoComSaldoInsuficienteRetornaHttpStatus422(boolean isUsuarioAutenticado, int statusHttpEsperado) throws Exception {
        assertNotNull(novaTransacaoSaldoInsuficiente);
        doThrow(SaldoInsuficienteException.class).when(transacoesService).realizarTransacao(novaTransacaoSaldoInsuficiente);
        performMockMvcRequestAndExpectHttpStatus(isUsuarioAutenticado,"POST", BASE_URL, novaTransacaoSaldoInsuficiente, statusHttpEsperado);
    }
}
