package com.crypto.candlestick.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "ticks")
public class CandleStick {

    @JsonProperty(value = "t")
    private Long timestamp;
    @JsonProperty(value = "o")
    private BigDecimal open;
    @JsonProperty(value = "c")
    private BigDecimal close;
    @JsonProperty(value = "h")
    private BigDecimal high;
    @JsonProperty(value = "l")
    private BigDecimal low;
    @JsonProperty(value = "v")
    private BigDecimal volume;

    //Underlying ticks which generated the candlestick
    private List<Tick> ticks;
}

