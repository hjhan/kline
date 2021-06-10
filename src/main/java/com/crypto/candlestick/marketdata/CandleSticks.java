package com.crypto.candlestick.marketdata;

import com.crypto.candlestick.domain.CandleStick;

import java.io.InputStream;

public class CandleSticks {

    public ResponseBase<CandleStick> parseTrades(InputStream inputStream) {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parseResponse(inputStream, CandleStick.class);
    }

}
