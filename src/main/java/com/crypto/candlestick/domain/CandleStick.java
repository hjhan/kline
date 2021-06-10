package com.crypto.candlestick.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}

