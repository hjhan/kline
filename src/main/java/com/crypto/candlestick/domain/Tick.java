package com.crypto.candlestick.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TRADE")
public class Tick {

    @Id
    @JsonProperty(value = "d")
    private String id;

    @Column
    @JsonProperty(value = "p")
    private BigDecimal price;

    @Column
    @JsonProperty(value = "q")
    private BigDecimal quantity;

    @Column
    @JsonProperty(value = "s")
    private Side side;

    @Column(name = "TS")
    @JsonProperty(value = "t")
    private Long ts;

    @Column
    @JsonProperty(value = "i")
    private String instrument;
}
