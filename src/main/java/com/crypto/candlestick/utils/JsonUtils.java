package com.crypto.candlestick.utils;

import com.crypto.candlestick.marketdata.ResponseBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class JsonUtils {

    static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> ResponseBase<T> parseResponse(InputStream inputStream, Class<?> cls) {
        JavaType responseType = objectMapper.getTypeFactory().constructParametricType(ResponseBase.class, cls);
        try {
            return objectMapper.readValue(inputStream, responseType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T strToObject(String content, Class<T> valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String objectToJsonStr(Object object) {
        String jsonString;
        try {
            jsonString = objectMapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonString;
    }
}
