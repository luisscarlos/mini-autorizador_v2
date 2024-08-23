package com.miniautorizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class BaseControllerTest {

    private static final String APPLICATION_JSON = "application/json";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @Value("${spring.security.user.roles}")
    private String defaultRole;

    void performMockMvcRequestAndExpectHttpStatus(boolean isUsuarioAutenticado, String metodoHttp, String url, Object body, int httpStatusEsperado) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = criaRequestBuilder(metodoHttp, url);
        configuraRequestBody(requestBuilder, body);
        configuraAutenticacao(requestBuilder, isUsuarioAutenticado);
        mockMvc.perform(requestBuilder).andExpect(getStatusMatcher(httpStatusEsperado));
    }

    private MockHttpServletRequestBuilder criaRequestBuilder(String metodoHttp, String url) {
        return switch (metodoHttp.toUpperCase()) {
            case "POST" -> post(url);
            case "GET" -> get(url);
            case "PUT" -> put(url);
            case "DELETE" -> delete(url);
            default -> throw new IllegalArgumentException("Método HTTP não suportado: " + metodoHttp);
        };
    }

    private void configuraRequestBody(MockHttpServletRequestBuilder requestBuilder, Object body) throws Exception {
        if (Objects.nonNull(body)) {
            requestBuilder.contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(body));
        } else {
            requestBuilder.contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON);
        }
    }

    private void configuraAutenticacao(MockHttpServletRequestBuilder requestBuilder, boolean isUsuarioAutenticado) {
        if (Boolean.TRUE.equals(isUsuarioAutenticado)) {
            requestBuilder.with(user(username).password(password).roles(defaultRole));
        }
    }

    private ResultMatcher getStatusMatcher(int status) {
        return switch (status) {
            case 401 -> MockMvcResultMatchers.status().isUnauthorized();
            case 201 -> MockMvcResultMatchers.status().isCreated();
            case 403 -> MockMvcResultMatchers.status().isForbidden();
            case 200 -> MockMvcResultMatchers.status().isOk();
            case 422 -> MockMvcResultMatchers.status().isUnprocessableEntity();
            case 404 -> MockMvcResultMatchers.status().isNotFound();
            default -> throw new IllegalArgumentException("Unsupported HTTP status: " + status);
        };
    }
}
