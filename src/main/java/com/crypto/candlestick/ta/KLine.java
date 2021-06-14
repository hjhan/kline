package com.crypto.candlestick.ta;

import com.crypto.candlestick.domain.CandleStick;
import com.crypto.candlestick.domain.Tick;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class KLine {

    private Long roundToMin(Long ts) {
        return (ts / 1000 / 60 ) * 1000 * 60;  //End? time of candlestick (Unix timestamp)
    }

    public NavigableMap<Long, CandleStick> generateKLine(List<Tick> ticks) {
        // K timestamp of every interval(1m), ordered by ts
        // other intervals can be generated from 1m likewise
        NavigableMap<Long, CandleStick> groupedTicks = new TreeMap<>();

        Map<Long, List<Tick>> ticksToReduce = ticks.stream().map(mapToMin()).collect(Collectors.groupingBy(Tick::getTs));
        for (Map.Entry<Long, List<Tick>> e : ticksToReduce.entrySet()) {
            CandleStick candleStick = reduceToCandleStick(e.getValue());
            candleStick.setTimestamp(e.getKey());
            groupedTicks.put(e.getKey(), candleStick);
        }
        return groupedTicks;
    }

    public CandleStick reduceToCandleStick(List<Tick> ticks) {
        int size = ticks.size();
        CandleStick candleStick = new CandleStick();
        candleStick.setTicks(ticks);
        if (size > 0) {
            //ticks are trade revers ordered, so the first one would be close
            candleStick.setClose(ticks.get(0).getPrice());
            candleStick.setOpen(ticks.get(size - 1).getPrice());
            BigDecimal high = ticks.get(0).getPrice();
            BigDecimal low = ticks.get(0).getPrice();
            BigDecimal vol = ticks.get(0).getQuantity();
            for (int i = 1; i < size; i++) {
                BigDecimal price = ticks.get(i).getPrice();
                if (price.compareTo(high) > 0) {
                    high = price;
                }
                if (price.compareTo(low) < 0) {
                    low = price;
                }
                vol = vol.add(ticks.get(i).getQuantity());
            }
            candleStick.setHigh(high);
            candleStick.setLow(low);
            candleStick.setVolume(vol);

        } else {
            // if no trades use BigDecimal.ZERO as vol to indicate and price set to previous close
            candleStick.setVolume(BigDecimal.ZERO);
        }
        return candleStick;
    }

    private Function<Tick, Tick> mapToMin() {
        return tick -> {
            tick.setTs(roundToMin(tick.getTs()));
            return tick;
        };
    }

    public NavigableMap<Long, CandleStick> generateKLineFrom1m(List<CandleStick> candleSticks,Interval interval) {
        NavigableMap<Long, CandleStick> result = new TreeMap<>();
        for (CandleStick cs: candleSticks) {
            cs.setTimestamp(cs.getTimestamp() / interval.getNMinutes() * interval.getNMinutes());
        }
        Map<Long, List<CandleStick>> groupedCandleSticks = candleSticks.stream().collect(Collectors.groupingBy(CandleStick::getTimestamp));
        for (Map.Entry<Long, List<CandleStick>> e: groupedCandleSticks.entrySet() ) {
            CandleStick candleStick = reduce(e.getValue());
            candleStick.setTimestamp(e.getKey());
            result.put(e.getKey(),candleStick);
        }
        return result;
    }

    private CandleStick reduce(List<CandleStick> candleSticks) {
        int size = candleSticks.size();
        //candleSticks ascending order
        CandleStick candleStick = new CandleStick();
        if (size > 0) {
            candleStick.setOpen(candleSticks.get(0).getOpen());
            candleStick.setClose(candleSticks.get(size-1).getClose());

            BigDecimal high = candleSticks.get(0).getHigh();
            BigDecimal low = candleSticks.get(0).getLow();
            BigDecimal vol = candleSticks.get(0).getVolume();
            for (int i = 1; i < size; i++) {
                BigDecimal h = candleSticks.get(i).getHigh();
                BigDecimal l = candleSticks.get(i).getLow();
                if (h.compareTo(high) > 0) {
                    high = h;
                }
                if (l.compareTo(low) < 0) {
                    low = l;
                }
                vol = vol.add(candleSticks.get(i).getVolume());
            }
            candleStick.setHigh(high);
            candleStick.setLow(low);
            candleStick.setVolume(vol);
        }else{
            candleStick.setVolume(BigDecimal.ZERO);
        }
        return candleStick;
    }

}
