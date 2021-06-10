package com.crypto.candlestick.marketdata;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class JsonParser {

    static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> ResponseBase<T> parseResponse(InputStream inputStream, Class<?> cls) {
        JavaType responseType = objectMapper.getTypeFactory().constructParametricType(ResponseBase.class, cls);
        try {
            return objectMapper.readValue(inputStream, responseType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
