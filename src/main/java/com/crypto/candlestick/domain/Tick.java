package com.crypto.candlestick.domain;

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
    private String id;
    private BigDecimal price;
    private BigDecimal quantity;
    private Side  side;
    private Long timestamp;
}
