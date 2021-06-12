package com.crypto.candlestick.marketdata;

import com.crypto.candlestick.domain.Tick;
import com.crypto.candlestick.repo.TradeRepository;
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

import static com.crypto.candlestick.utils.Const.URL_BASE;

@Component
public class Crawler {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Trades trades;

    @Autowired
    private TradeRepository tradeRepository;


    public String getTrades() {
        String url = URL_BASE + "get-trades?depth=1000&instrument_name=BTC_USDT";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        return responseEntity.getBody();
    }

    @Scheduled(fixedRate = 1000 * 10)
    public void getDayKline() {
        LOG.info("==定时任务开始==");
        String tradeStr = getTrades();
        LOG.info(tradeStr);


        InputStream inputStream = new ByteArrayInputStream(tradeStr.getBytes());
        ResponseBase<Tick> tickResponseBase = trades.parseTrades(inputStream);
        List<Tick> ticks = tickResponseBase.getResult().getData();
        for (Tick t: ticks) {
            tradeRepository.save(t);
        }


        LOG.info("==定时任务结束==");
    }
}
