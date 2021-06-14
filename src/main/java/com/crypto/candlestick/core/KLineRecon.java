package com.crypto.candlestick.core;

import com.crypto.candlestick.domain.CandleStick;
import com.crypto.candlestick.domain.Tick;
import com.crypto.candlestick.ta.Interval;
import com.crypto.candlestick.ta.KLine;
import com.crypto.candlestick.utils.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

import static com.crypto.candlestick.utils.FileUtils.getCandleSticksFromFile;
import static com.crypto.candlestick.utils.FileUtils.getTickListFromFile;

@Component
public class KLineRecon {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KLine kLine;

    public ReconResult recon(String klineFile,String tradesFile) {
        //1. get CandleStick from File
        List<CandleStick> crawledKlines = getCandleSticksFromFile(klineFile);

        //2. Get Trades from file and generate CandleSticks to be compared
        List<Tick> tickList = getTickListFromFile(tradesFile);
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

}
