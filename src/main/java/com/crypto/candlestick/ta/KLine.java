package com.crypto.candlestick.ta;

import com.crypto.candlestick.domain.Tick;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class KLine {

    private Long roundToMin(Long ts){
        return ts / 1000 / 60;
    }



    public void groupTicks(List<Tick> ticks){
        // K timestamp of every interval(1m), ordered by ts
        NavigableMap<Long,List<Tick>> groupedTicks = new TreeMap<>();


        Map<Long, List<Tick>> collect = ticks.stream().map(mapToMin()).collect(Collectors.groupingBy(Tick::getTimestamp));
        System.out.println(collect);

    }

    private Function<Tick, Tick> mapToMin() {
        return tick -> {
            tick.setTimestamp(roundToMin(tick.getTimestamp()));
            return tick;
        };
    }

}
