package com.crypto.candlestick.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    @JsonProperty(value = "instrument_name")
    private String instrumentName;
    private List<T> data;

}
