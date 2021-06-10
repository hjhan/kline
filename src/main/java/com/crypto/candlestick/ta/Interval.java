package com.crypto.candlestick.ta;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Interval {

    ONE_MIN("1m"),FIVE_MIN("5m"),FIFTEEN_MIN("15m"),
    THIRTY_MIN("30m"),ONE_HOUR("1h"),FOUR_HOUR("4h"),
    SIX_HOUR("6h"),TWELVE_HOUR("12h"),ONE_DAY("1D"),
    SEVEN_DAY("7D"),FOURTEEN_DAY("14D"),ONE_MON("1M");

    private String value;

}
