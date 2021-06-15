package com.crypto.candlestick.core;

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

@SpringBootTest
class KLineReconTest {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    KLineRecon kLineRecon;

    @Test
    void recon() {
        ReconResult reconResult = kLineRecon.recon("data/input/kline-202106141730.json","data/input/tradesJsons-202106141730.txt");

        String resultJson = JsonUtils.objectToJsonStr(reconResult);
        System.out.println(resultJson);//dump the reconciliation result as json to visualize the consistency
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
                LOG.info(second.getTicks().toString());
                //Assertions.assertTrue(first.getVolume().compareTo(second.getVolume()) == 0, "Volume not match");
                Assertions.assertTrue(first.getOpen().compareTo(second.getOpen()) == 0,"Open not match");
                Assertions.assertTrue(first.getClose().compareTo(second.getClose())==0,"Close not match");
                Assertions.assertTrue(first.getHigh().compareTo(second.getHigh())==0,"High not match");
                Assertions.assertTrue(first.getLow().compareTo(second.getLow())==0,"Low not match");
            }
        }
    }
}