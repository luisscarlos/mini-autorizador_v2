package com.miniautorizador.service;

import com.miniautorizador.exception.CartaoDuplicadoException;
import com.miniautorizador.exception.CartaoInexistenteException;
import com.miniautorizador.exception.CartaoInvalidoException;
import com.miniautorizador.model.Cartao;
import com.miniautorizador.repository.CartaoRepository;
import com.miniautorizador.schema.CartaoResponse;
import com.miniautorizador.schema.CriarCartao;
import com.miniautorizador.util.CartaoCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CartaoServiceTest {

    @InjectMocks
    private CartaoService cartaoService;

    @Mock
    private CartaoRepository cartaoRepository;

    private final Cartao cartaoPadraoEntidade = CartaoCreator.cartaoPadraoEntidade();

    private final Cartao cartaoCorretoNaoDuplicadoEntidade = CartaoCreator.cartaoCorretoNaoDuplicadoEntidade();

    private final CriarCartao novoCartaoComAlfaNumerico = CartaoCreator.novoCartaoComAlfaNumerico();

    private final CriarCartao novoCartaoCorreto = CartaoCreator.novoCartaoCorreto();

    private final CriarCartao novoCartaoCorretoNaoDuplicado = CartaoCreator.novoCartaoCorretoNaoDuplicado();

    private final CartaoResponse cartaoResponse = CartaoCreator.cartaoResponse();

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
//        given(cartaoRepository.findByNumeroCartao(any(String.class))).willReturn(Optional.empty());
//
//        CriarCartao x = CriarCartao.builder()
//                .numeroCartao("6549873885634229")
//                .senha("1234")
//                .build();
//
////        cartaoService.criarCartao(x);
//
//        cartaoRepository.save(cartaoPadraoEntidade);
//
//        CriarCartao c = CriarCartao.builder()
//                .numeroCartao("9999999999999993")
//                .senha("4321")
//                .build();
//

//
//        when(cartaoService.criarCartao(c)).thenReturn(cr);
//
//        verify(cartaoRepository).save(cartaoCorretoNaoDuplicadoEntidade);

        when(cartaoRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.empty());

//        CartaoResponse cr = new CartaoResponse();
//        cr.setNumeroCartao("9999999999999999");
//        cr.setSenha("4321");

//        cartaoService.criarCartao(novoCartaoCorretoNaoDuplicado);
        when(cartaoService.criarCartao(novoCartaoCorretoNaoDuplicado)).thenReturn(cartaoResponse);
        assertEquals(cartaoService.criarCartao(novoCartaoCorretoNaoDuplicado), cartaoResponse);
    }

    @Test
    void quandoCartaoNaoExistisThrowCartaoInexistenteException() {
        when(cartaoRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.empty());

        assertThrows(CartaoInexistenteException.class,
                () -> cartaoService.obterSaldoCartao("9999999999999993"));
    }

}
