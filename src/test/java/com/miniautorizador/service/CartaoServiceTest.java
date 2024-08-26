package com.miniautorizador.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miniautorizador.domain.Cartao;
import com.miniautorizador.dto.entrada.CriarCartao;
import com.miniautorizador.exception.CartaoDuplicadoException;
import com.miniautorizador.exception.CartaoInexistenteSaldoException;
import com.miniautorizador.exception.CartaoInvalidoException;
import com.miniautorizador.repository.CartaoRepository;
import com.miniautorizador.util.CartaoBuilder;
import com.miniautorizador.util.JsonConverter;
import com.miniautorizador.validation.validators.CartaoAlfanumericoValidator;
import com.miniautorizador.validation.validators.CartaoDuplicadoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CartaoServiceTest {

    @Mock
    private CartaoAlfanumericoValidator cartaoAlfanumericoValidator;

    @Mock
    private CartaoDuplicadoValidator cartaoDuplicadoValidator;

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private CartaoService cartaoService;

    /*
    * O ArgumentCaptor tem o papel de pegar o argumento exato que foi passado como parâmetro de uma função,
    * nesse caso é usado no teste quandoCartaoValidoSalvarCartao para verificarmos se o objeto Cartão foi saldo.
    * Se o ArgumentCaptor não for usado e tentarmos fazer um verify com a variável cartaoPadraoEntidade (tipo Cartao) por exemplo,
    * teremos um erro informando que os argumentos são diferentes, mesmo que seu conteúdo seja igual, isso acontece
    * porque a referência na memória é diferente para o save que acontece no service e o verify save do teste, e com
    * o ArgumentCaptor, obtemos o mesmo argumento da service com referência de memória e valores preenchidos para validar
    * que a operação foi executada com sucesso.
    * */
    @Captor
    ArgumentCaptor<Cartao> cartaoCaptor;

    @InjectMocks
    private JsonConverter jsonConverter;

    private Cartao cartaoPadraoEntidade;

    private CriarCartao novoCartaoComAlfaNumerico;

    private CriarCartao novoCartaoCorreto;

    @BeforeEach
    void setup() throws JsonProcessingException {
        MockitoAnnotations.openMocks(this);
        CartaoBuilder cartaoBuilder = new CartaoBuilder(jsonConverter);
        cartaoPadraoEntidade = cartaoBuilder.cartaoPadraoEntidade();
        novoCartaoComAlfaNumerico = cartaoBuilder.novoCartaoComAlfaNumerico();
        novoCartaoCorreto = cartaoBuilder.novoCartaoCorreto();

        cartaoAlfanumericoValidator.setProximoValidador(cartaoDuplicadoValidator);
    }

    @Test
    void quandoAlfanumericoNoNumeroCartaoThrowsCartaoInvalidoException() {
        assertThrows(CartaoInvalidoException.class,
                () -> cartaoService.criarCartao(novoCartaoComAlfaNumerico));
    }

    @Test
    void quandoCartaoExistirThrowsCartaoDuplicadoException() {
        when(cartaoRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.of(cartaoPadraoEntidade));

        assertThrows(CartaoDuplicadoException.class,
                () -> cartaoService.criarCartao(novoCartaoCorreto));
    }

    @Test
    void quandoCartaoValidoSalvarCartao() {
        when(cartaoRepository.save(any(Cartao.class))).thenReturn(cartaoPadraoEntidade);
        cartaoService.criarCartao(novoCartaoCorreto);

        verify(cartaoAlfanumericoValidator, times(1)).validar(novoCartaoCorreto);
        verify(cartaoDuplicadoValidator, times(1)).validar(novoCartaoCorreto);
        verify(cartaoRepository).save(cartaoCaptor.capture());
    }

    @Test
    void quandoCartaoNaoExistisThrowCartaoInexistenteException() {
        when(cartaoRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.empty());

        assertThrows(CartaoInexistenteSaldoException.class,
                () -> cartaoService.obterSaldoCartao("9999999999999993"));
    }

    @Test
    void quandoCartaoExistirRetornarSaldoDoCartao() {
        when(cartaoRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.of(cartaoPadraoEntidade));
        assertEquals(BigDecimal.valueOf(500), cartaoService.obterSaldoCartao(cartaoPadraoEntidade.getNumeroCartao()));
    }
}
