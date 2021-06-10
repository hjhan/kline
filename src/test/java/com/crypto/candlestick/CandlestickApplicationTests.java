package com.crypto.candlestick;

import com.crypto.candlestick.domain.Tick;
import com.crypto.candlestick.marketdata.ResponseBase;
import com.crypto.candlestick.ta.KLine;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@SpringBootTest
class CandlestickApplicationTests {

    static ObjectMapper objectMapper = new ObjectMapper();

	public LocalDateTime tsToDatetime(long timestamp){
		Instant instant = Instant.ofEpochMilli(timestamp);
		ZoneId zone = ZoneId.systemDefault();
		System.out.println(zone);
		return LocalDateTime.ofInstant(instant, zone);
	}

    @BeforeAll
    static void setup() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    void kline_1m() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("/trades/btc_usdt.json");

        //JsonNode jsonNode = objectMapper.readTree(fileInputStream);
        JavaType tradeResponse = objectMapper.getTypeFactory().constructParametricType(ResponseBase.class, Tick.class);
        ResponseBase<Tick> value = objectMapper.readValue(inputStream, tradeResponse);

        List<Tick> ticks = value.getResult().getData();

        KLine kLine = new KLine();
        kLine.groupTicks(ticks);

        for (Tick tick : ticks) {
			System.out.println(tick);
			System.out.println(tsToDatetime(tick.getTimestamp()*1000*60));
        }


    }

}
