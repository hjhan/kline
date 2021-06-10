package com.crypto.candlestick.marketdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.crypto.candlestick.utils.Const.URL_BASE;

@Component
public class Crawler {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    public String getTrades() {
        String url =  URL_BASE + "get-trades?depth=1000&instrument_name=BTC_USDT";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        return responseEntity.getBody();
    }

    @Scheduled(fixedRate = 1000 * 10)
    public void getDayKline() {
        LOG.info("==定时任务开始==");
        String trades = getTrades();
        LOG.info(trades);

        LOG.info("==定时任务结束==");
    }
}
