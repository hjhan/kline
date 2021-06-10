package com.crypto.candlestick.marketdata;

import com.crypto.candlestick.domain.CandleStick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class CandleSticks {

    @Autowired
    private JsonParser jsonParser;

    public ResponseBase<CandleStick> parse(InputStream inputStream) {
        return jsonParser.parseResponse(inputStream, CandleStick.class);
    }

}
