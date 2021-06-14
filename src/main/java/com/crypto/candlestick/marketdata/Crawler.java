package com.crypto.candlestick.marketdata;

import com.crypto.candlestick.domain.Tick;
import com.crypto.candlestick.repo.TradeRepository;
import com.crypto.candlestick.ta.Interval;
import com.crypto.candlestick.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;

import static com.crypto.candlestick.utils.Const.BTC_USDT;
import static com.crypto.candlestick.utils.Const.URL_BASE;

@Component
@EnableScheduling
@Profile({"dev"})
public class Crawler {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private HashSet<String> tradeIds = new HashSet<>();
    private FileWriter tradeWriter;
    private FileWriter kLineWriter;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TradeRepository tradeRepository;

    public String getCandleSticks(String instrumentName, Interval interval) {
        String url = URL_BASE + "get-candlestick?depth=1000&instrument_name=" + instrumentName + "&timeframe=" + interval.getValue();
        return getJson(url);
    }

    public List<Tick> getTrades(String instrumentName) {
        String url = URL_BASE + "get-trades?depth=1000&instrument_name=" + instrumentName;
        ResponseBase<Tick> tickResponseBase = JsonUtils.parseResponse(getInputStream(url), Tick.class);
        return tickResponseBase.getResult().getData();
    }

    //Inital delay to wait the trades to accumulate 20minutes, every 8hour,only need once
    @Scheduled(initialDelay = 1000*60*20, fixedRate = 1000 * 60 * 60 * 8)
    public void saveKlinesToFile() {
        saveKlineToFile();
    }

    @Scheduled(fixedRate = 1000 * 10) //every 10seconds
    public void saveTradesToFile() {
        LOG.info("==定时任务开始==");
        List<Tick> ticks = getTrades(BTC_USDT);
        for(int i = ticks.size()-1; i >= 0; i--){
            Tick t = ticks.get(i);
            if(!tradeIds.contains(t.getId())){
                tradeIds.add(t.getId());
                writeToFile(tradeWriter, JsonUtils.objectToJsonStr(t) + "\n");
                LOG.info("save tradeid " + t.getId());
            }
        }
        LOG.info("**********定时任务结束*********");
    }

    private void writeToFile(FileWriter writer, String s) {
        try {
            writer.write(s);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveKlineToFile()  {
        String candleSticks = getCandleSticks(BTC_USDT, Interval.ONE_MIN);
        writeToFile(kLineWriter,candleSticks);
        LOG.info("Saved Kline to file");
    }


    private InputStream getInputStream(String url) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String body = responseEntity.getBody();
        return new ByteArrayInputStream(body.getBytes());
    }

    private String getJson(String url) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        return responseEntity.getBody();
    }


    @PostConstruct
    private void init(){
        LOG.info("***********************************");
        try {
            tradeWriter = new FileWriter("tradesJsons.txt", false);
            kLineWriter = new FileWriter("kline.json",false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
