package com.miniautorizador.service;

import com.miniautorizador.exception.CartaoDuplicadoException;
import com.miniautorizador.exception.CartaoInexistenteSaldoException;
import com.miniautorizador.exception.CartaoInvalidoException;
import com.miniautorizador.model.Cartao;
import com.miniautorizador.repository.CartaoRepository;
import com.miniautorizador.schema.CriarCartao;
import com.miniautorizador.util.CartaoBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CartaoServiceTest {

    @InjectMocks
    private CartaoService cartaoService;

    @Mock
    private CartaoRepository cartaoRepository;

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

    private final Cartao cartaoPadraoEntidade = CartaoBuilder.cartaoPadraoEntidade();

    private final CriarCartao novoCartaoComAlfaNumerico = CartaoBuilder.novoCartaoComAlfaNumerico();

    private final CriarCartao novoCartaoCorreto = CartaoBuilder.novoCartaoCorreto();

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
        cartaoService.criarCartao(novoCartaoCorreto);
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
