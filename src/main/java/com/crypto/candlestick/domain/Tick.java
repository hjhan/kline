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
public class Tick {

    @JsonProperty(value = "d")
    private String id;

    @JsonProperty(value = "p")
    private BigDecimal price;

    @JsonProperty(value = "q")
    private BigDecimal quantity;

    @JsonProperty(value = "s")
    private Side side;

    @JsonProperty(value = "t")
    private Long timestamp;

    @JsonProperty(value = "i")
    private String instrument;
}
