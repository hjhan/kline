package com.crypto.candlestick.core;

import com.crypto.candlestick.domain.CandleStick;
import com.crypto.candlestick.ta.Interval;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReconResult {

    private Interval interval;
    private String instrumentName;

    private List<Pair<CandleStick,CandleStick>> data;
}
