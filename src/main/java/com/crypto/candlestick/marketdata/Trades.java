package com.crypto.candlestick.marketdata;


import com.crypto.candlestick.domain.Tick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class Trades {
    @Autowired
    private JsonParser jsonParser;

    public ResponseBase<Tick> parseTrades(InputStream inputStream) {
        return jsonParser.parseResponse(inputStream, Tick.class);
    }
}
