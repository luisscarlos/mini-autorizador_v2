package com.miniautorizador.contract;

import com.miniautorizador.schema.CartaoResponse;
import com.miniautorizador.schema.CriarCartao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Tag(name = "1. Cartão", description = "Gerenciamento de cartões.")
@RequestMapping(value = "/cartoes")
public interface CartaoContract {

    @Operation(summary = "Cria um novo cartão")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = CartaoResponse.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = CartaoResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    ResponseEntity<CartaoResponse> criarCartao(@RequestBody @Valid CriarCartao cartao);

    @Operation(summary = "Consultar saldo do cartão")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = BigDecimal.class))),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping(value = "/{numeroCartao}")
    ResponseEntity<BigDecimal> obterSaldoCartao(@RequestParam @Valid @NotNull String numeroCartao);


}
