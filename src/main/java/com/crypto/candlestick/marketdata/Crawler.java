package com.crypto.candlestick.marketdata;

import com.crypto.candlestick.domain.CandleStick;
import com.crypto.candlestick.domain.Tick;
import com.crypto.candlestick.repo.TradeRepository;
import com.crypto.candlestick.ta.Interval;
import com.crypto.candlestick.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static com.crypto.candlestick.utils.Const.BTC_USDT;
import static com.crypto.candlestick.utils.Const.URL_BASE;

@Component
public class Crawler {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TradeRepository tradeRepository;

    public List<CandleStick> getCandleSticks(String instrumentName, Interval interval) {
        String url = URL_BASE + "get-candlestick?depth=1000&instrument_name=" + instrumentName + "&timeframe=" + interval.getValue();
        ResponseBase<CandleStick> candleStickResponse = JsonUtils.parseResponse(getInputStream(url), CandleStick.class);
        return candleStickResponse.getResult().getData();
    }

    public List<Tick> getTrades(String instrumentName) {
        String url = URL_BASE + "get-trades?depth=1000&instrument_name=" + instrumentName;
        ResponseBase<Tick> tickResponseBase = JsonUtils.parseResponse(getInputStream(url), Tick.class);
        return tickResponseBase.getResult().getData();
    }

    @Scheduled(fixedRate = 1000 * 10)
    public void saveTradesToDB() {
        LOG.info("==定时任务开始==");
        List<Tick> ticks = getTrades(BTC_USDT);
        for (Tick t : ticks) {
            tradeRepository.save(t);
        }

        LOG.info("==定时任务结束==");
    }


    private InputStream getInputStream(String url) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        return new ByteArrayInputStream(responseEntity.getBody().getBytes());
    }
}
