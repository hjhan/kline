package com.crypto.candlestick.marketdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBase<T> {
    private int code;
    private String method;
    private Result<T> result;

}
