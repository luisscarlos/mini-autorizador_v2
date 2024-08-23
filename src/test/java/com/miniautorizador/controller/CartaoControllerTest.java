package com.miniautorizador.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miniautorizador.config.SecurityConfig;
import com.miniautorizador.dto.entrada.CriarCartao;
import com.miniautorizador.exception.CartaoDuplicadoException;
import com.miniautorizador.exception.CartaoInexistenteSaldoException;
import com.miniautorizador.exception.CartaoInvalidoException;
import com.miniautorizador.service.CartaoService;
import com.miniautorizador.util.CartaoBuilder;
import com.miniautorizador.util.JsonConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = CartaoController.class)
@ContextConfiguration(classes = { SecurityConfig.class, CartaoController.class })
class CartaoControllerTest extends BaseControllerTest {

    @MockBean
    private CartaoService cartaoService;

    @InjectMocks
    private JsonConverter jsonConverter;

    private CriarCartao cartaoPadraoDuplicado;

    private CriarCartao novoCartaoCorreto;

    private CriarCartao novoCartaoComAlfaNumerico;

    private static final String BASE_URL = "/cartoes";

    private static final String NUMERO_CARTAO_VALIDO = "1149873445634233";

    private static final String NUMERO_CARTAO_INEXISTENTE = "3333333333333333";

    @BeforeEach
    void setup() throws JsonProcessingException {
        CartaoBuilder cartaoBuilder = new CartaoBuilder(jsonConverter);
        cartaoPadraoDuplicado = cartaoBuilder.cartaoPadraoDuplicado();
        novoCartaoCorreto = cartaoBuilder.novoCartaoCorreto();
        novoCartaoComAlfaNumerico = cartaoBuilder.novoCartaoComAlfaNumerico();
    }

    @ParameterizedTest
    @CsvSource({
            "true, 201",
            "false, 401"
    })
    void quandoCartaoCriadoComSucessoRetornaHttpStatusCode201(boolean isUsuarioAutenticado, int statusHttpEsperado) throws Exception {
        assertNotNull(novoCartaoCorreto);
        performMockMvcRequestAndExpectHttpStatus(isUsuarioAutenticado,"POST", BASE_URL, novoCartaoCorreto, statusHttpEsperado);
    }

    @ParameterizedTest
    @CsvSource({
            "true, 422",
            "false, 401"
    })
    void quandoCartaoDuplicadoRetornaHttpStatusCode422(boolean isUsuarioAutenticado, int statusHttpEsperado) throws Exception {
        assertNotNull(cartaoPadraoDuplicado);
        when(cartaoService.criarCartao(cartaoPadraoDuplicado)).thenThrow(CartaoDuplicadoException.class);

        performMockMvcRequestAndExpectHttpStatus(isUsuarioAutenticado,"POST", BASE_URL, cartaoPadraoDuplicado, statusHttpEsperado);
    }

    @ParameterizedTest
    @CsvSource({
            "true, 422",
            "false, 401"
    })
    void quandoCartaoCriadoComAlfanumericoRetornaHttpStatusCode422(boolean isUsuarioAutenticado, int statusHttpEsperado) throws Exception {
        assertNotNull(novoCartaoComAlfaNumerico);
        when(cartaoService.criarCartao(novoCartaoComAlfaNumerico)).thenThrow(CartaoInvalidoException.class);

        performMockMvcRequestAndExpectHttpStatus(isUsuarioAutenticado,"POST", BASE_URL, novoCartaoComAlfaNumerico, statusHttpEsperado);
    }

    @ParameterizedTest
    @CsvSource({
            "true, 200",
            "false, 401"
    })
    void quandoCartaoValidoRetornaSaldoCartaoRetornaHttpStatusCode200(boolean isUsuarioAutenticado, int statusHttpEsperado) throws Exception {
        assertNotNull(cartaoService);
        when(cartaoService.obterSaldoCartao(NUMERO_CARTAO_VALIDO)).thenReturn(BigDecimal.valueOf(500));

        String url = String.format("%s/%s", BASE_URL, NUMERO_CARTAO_VALIDO);
        performMockMvcRequestAndExpectHttpStatus(isUsuarioAutenticado,"GET", url, null, statusHttpEsperado);
    }

    @ParameterizedTest
    @CsvSource({
            "true, 404",
            "false, 401"
    })
    void quandoCartaoInexistenteAoObterSaldoRetornaHttpStatus404(boolean isUsuarioAutenticado, int statusHttpEsperado) throws Exception {
        assertNotNull(cartaoService);
        when(cartaoService.obterSaldoCartao(NUMERO_CARTAO_INEXISTENTE)).thenThrow(CartaoInexistenteSaldoException.class);

        String url = String.format("%s/%s", BASE_URL, NUMERO_CARTAO_INEXISTENTE);
        performMockMvcRequestAndExpectHttpStatus(isUsuarioAutenticado,"GET", url, null, statusHttpEsperado);
    }
}
