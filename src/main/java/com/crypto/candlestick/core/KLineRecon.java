package com.crypto.candlestick.core;

import com.crypto.candlestick.domain.CandleStick;
import com.crypto.candlestick.domain.Tick;
import com.crypto.candlestick.marketdata.Crawler;
import com.crypto.candlestick.repo.TradeRepository;
import com.crypto.candlestick.ta.Interval;
import com.crypto.candlestick.ta.KLine;
import com.crypto.candlestick.utils.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

@Component
public class KLineRecon {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private KLine kLine;

    @Autowired
    private Crawler crawler;

    public ReconResult recon(){
        Sort sort = Sort.by(Sort.Direction.DESC, "ts","id");
        List<Tick> tickList = tradeRepository.findAll(sort);

        NavigableMap<Long, CandleStick> generatedKlines = kLine.generateKLine(tickList);

        List<CandleStick> crawledKlines = crawler.getCandleSticks(Const.BTC_USDT,Interval.ONE_MIN);

        ReconResult reconResult = new ReconResult();
        reconResult.setInterval(Interval.ONE_MIN);
        reconResult.setInstrumentName(Const.BTC_USDT);

        List<Pair<CandleStick,CandleStick>> data = new ArrayList<>();
        reconResult.setData(data);

        for (CandleStick cs : crawledKlines) {
            if (generatedKlines.containsKey(cs.getTimestamp())) {
                CandleStick csToCompare = generatedKlines.get(cs.getTimestamp());
                data.add(Pair.of(cs,csToCompare));
            }
        }
        return reconResult;
    }



}
