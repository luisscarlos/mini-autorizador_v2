package com.miniautorizador.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonConverter {

    private final ObjectMapper mapper;

    public JsonConverter() {
        this.mapper = new ObjectMapper();
    }

    public <T> T jsonParaObjetoJava(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(json, clazz);
    }

    public String objetoJavaParaJson(Object objeto) throws JsonProcessingException {
        return mapper.writeValueAsString(objeto);
    }
}
