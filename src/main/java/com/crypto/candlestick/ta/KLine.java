package com.crypto.candlestick.ta;

import com.crypto.candlestick.domain.CandleStick;
import com.crypto.candlestick.domain.Tick;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class KLine {

    private Long roundToMin(Long ts) {
        return (ts / 1000 / 60) * 1000 * 60;  //End time of candlestick (Unix timestamp)
    }

    public NavigableMap<Long, CandleStick> groupTicks(List<Tick> ticks) {
        // K timestamp of every interval(1m), ordered by ts
        // other intervals can be generated from 1m likewise
        NavigableMap<Long, CandleStick> groupedTicks = new TreeMap<>();

        Map<Long, List<Tick>> ticksToReduce = ticks.stream().map(mapToMin()).collect(Collectors.groupingBy(Tick::getTimestamp));
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

        } else { // if no trades use BigDecimal.ZERO as vol to indicate
            candleStick.setVolume(BigDecimal.ZERO);
        }
        return candleStick;
    }

    private Function<Tick, Tick> mapToMin() {
        return tick -> {
            tick.setTimestamp(roundToMin(tick.getTimestamp()));
            return tick;
        };
    }

}
