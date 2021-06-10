package com.crypto.candlestick;

import com.crypto.candlestick.domain.CandleStick;
import com.crypto.candlestick.domain.Tick;
import com.crypto.candlestick.marketdata.CandleSticks;
import com.crypto.candlestick.marketdata.ResponseBase;
import com.crypto.candlestick.marketdata.Trades;
import com.crypto.candlestick.ta.KLine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.List;
import java.util.NavigableMap;


@SpringBootTest
class CandlestickApplicationTests {

    @Autowired
    private Trades trades;

    @Autowired
    private CandleSticks candleSticks;

    @Autowired
    private KLine kLine;

    @BeforeAll
    static void setup() {

    }

    @Test
    void kline_1m() {

        List<CandleStick> candleSticks = klines().getResult().getData();
        List<Tick> ticks = trades().getResult().getData();
        NavigableMap<Long, CandleStick> kLinesFromTrades = kLine.groupTicks(ticks);

        for (CandleStick cs : candleSticks) {
            if (kLinesFromTrades.containsKey(cs.getTimestamp())) {
                CandleStick csToCompare = kLinesFromTrades.get(cs.getTimestamp());
              /*Assertions.assertTrue(cs.getClose().compareTo(csToCompare.getClose()) == 0);
              Assertions.assertTrue(cs.getOpen().compareTo(csToCompare.getOpen()) == 0);
              Assertions.assertTrue(cs.getHigh().compareTo(csToCompare.getHigh()) == 0);
              Assertions.assertTrue(cs.getLow().compareTo(csToCompare.getLow()) == 0);
              Assertions.assertTrue(cs.getVolume().compareTo(csToCompare.getVolume()) == 0);*/
                System.out.println(cs);
                System.out.println(csToCompare);
            }
        }
    }

    private ResponseBase<Tick> trades() {
        InputStream inputStream = this.getClass().getResourceAsStream("/trades/btc_usdt_6-10-1740.json");
        return trades.parseTrades(inputStream);
    }

    private ResponseBase<CandleStick> klines() {
        InputStream inputStream = this.getClass().getResourceAsStream("/candlestick/cs-6-10-1741.json");
        return candleSticks.parse(inputStream);
    }
}
