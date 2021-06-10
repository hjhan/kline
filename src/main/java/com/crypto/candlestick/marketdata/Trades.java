package com.crypto.candlestick.marketdata;


import com.crypto.candlestick.domain.Tick;

import java.io.InputStream;

public class Trades {

    public ResponseBase<Tick> parseTrades(InputStream inputStream) {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parseResponse(inputStream, Tick.class);
    }
}
