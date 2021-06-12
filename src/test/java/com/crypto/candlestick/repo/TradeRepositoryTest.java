package com.crypto.candlestick.repo;

import com.crypto.candlestick.domain.Side;
import com.crypto.candlestick.domain.Tick;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;


@SpringBootTest
class TradeRepositoryTest {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    public void saveTradeTest()  {
        Tick tick = new Tick();
        tick.setId("1526090175196016896");
        tick.setPrice(new BigDecimal("37209.65"));
        tick.setQuantity(new BigDecimal("0.000003"));
        tick.setSide(Side.BUY);
        tick.setTs(1623317831393L);
        tick.setInstrument("BTC_USDT");
        Tick savedTick = tradeRepository.save(tick);
        LOG.info(savedTick.toString());
        Assertions.assertNotNull(savedTick.getId());
    }

}