package com.crypto.candlestick.ta;

import com.crypto.candlestick.core.KLineRecon;
import com.crypto.candlestick.core.ReconResult;
import com.crypto.candlestick.domain.CandleStick;
import com.crypto.candlestick.utils.DateUtils;
import com.crypto.candlestick.utils.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class KLineTest {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    KLineRecon kLineRecon;

    @Test
    void generateKLineFrom1m() {

        String kLine1m = "data/input/kline-1m-202106142015.json";
        String kLine5m = "data/input/kline-5m-202106142015.json";
        ReconResult reconResult = kLineRecon.reconKline(kLine1m, kLine5m, Interval.FIVE_MIN);
        System.out.println(JsonUtils.objectToJsonStr(reconResult));

        List<Pair<CandleStick, CandleStick>> data = reconResult.getData();
        int size = data.size();
        LOG.info("Total size" + size);
        //Exclude the first and last one, as insufficient trades, only compare the ones in middle
        if(size > 2){
            //The First one's close must match
            Assertions.assertTrue(data.get(0).getFirst().getClose().compareTo(data.get(0).getSecond().getClose())==0,"Close not match");
            //The Last one's open must match
            Assertions.assertTrue(data.get(size-1).getFirst().getOpen().compareTo(data.get(size-1).getSecond().getOpen())==0,"Close not match");
            for(int i = 1; i < (size -1); i++ ){
                Pair<CandleStick, CandleStick> pair = data.get(i);
                CandleStick first = pair.getFirst();
                CandleStick second = pair.getSecond();
                LOG.info(DateUtils.tsToDatetime(first.getTimestamp()).toString()) ;
                LOG.info(first.toString());
                LOG.info(second.toString());
                Assertions.assertTrue(first.getVolume().compareTo(second.getVolume()) == 0, "Volume not match");
                Assertions.assertTrue(first.getOpen().compareTo(second.getOpen()) == 0,"Open not match");
                Assertions.assertTrue(first.getClose().compareTo(second.getClose())==0,"Close not match");
                Assertions.assertTrue(first.getHigh().compareTo(second.getHigh())==0,"High not match");
                Assertions.assertTrue(first.getLow().compareTo(second.getLow())==0,"Low not match");
            }
        }

    }
}