package com.crypto.candlestick.core;

import com.crypto.candlestick.domain.CandleStick;
import com.crypto.candlestick.domain.Tick;
import com.crypto.candlestick.marketdata.ResponseBase;
import com.crypto.candlestick.ta.Interval;
import com.crypto.candlestick.ta.KLine;
import com.crypto.candlestick.utils.Const;
import com.crypto.candlestick.utils.JsonUtils;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

@Component
public class KLineRecon {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KLine kLine;

    public ReconResult recon() {
        //1. get CandleStick from File
        List<CandleStick> crawledKlines = getCandleSticksFromFile();

        //2. Get Trades from file and generate CandleSticks to be compared
        List<Tick> tickList = getTickListFromFile();
        NavigableMap<Long, CandleStick> generatedKlines = kLine.generateKLine(tickList);


        ReconResult reconResult = new ReconResult();
        reconResult.setInterval(Interval.ONE_MIN);
        reconResult.setInstrumentName(Const.BTC_USDT);

        List<Pair<CandleStick, CandleStick>> data = new ArrayList<>();
        reconResult.setData(data);

        for (CandleStick cs : crawledKlines) {
            if (generatedKlines.containsKey(cs.getTimestamp())) {
                CandleStick csToCompare = generatedKlines.get(cs.getTimestamp());
                data.add(Pair.of(cs, csToCompare));
            }
        }
        return reconResult;
    }

    private List<CandleStick> getCandleSticksFromFile(){
        try {
            InputStream inputStream = new ByteArrayInputStream(Files.readAllBytes(Paths.get("kline.json")));
            ResponseBase<CandleStick> response = JsonUtils.parseResponse(inputStream, CandleStick.class);
            return response.getResult().getData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private List<Tick> getTickListFromFile() {
        Path path = Paths.get("tradesJsons.txt");
        List<Tick> tickList = new ArrayList<>();
        try (ReversedLinesFileReader reader = new ReversedLinesFileReader(path.toFile(), StandardCharsets.UTF_8)) {
            String line = reader.readLine();
            while(line != null){
                tickList.add(JsonUtils.strToObject(line, Tick.class));
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tickList;
    }
}
