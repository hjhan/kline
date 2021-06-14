package com.crypto.candlestick.ta;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Interval {

    ONE_MIN("1m",1),FIVE_MIN("5m",5),FIFTEEN_MIN("15m",15),
    THIRTY_MIN("30m",30),ONE_HOUR("1h",60),FOUR_HOUR("4h",240),
    SIX_HOUR("6h",360),TWELVE_HOUR("12h",720),ONE_DAY("1D",1440),
    SEVEN_DAY("7D",10080),FOURTEEN_DAY("14D",20160),ONE_MON("1M",43200);

    private String value;
    private int nMinutes;

}
