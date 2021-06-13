package com.crypto.candlestick.repo;

import com.crypto.candlestick.domain.Tick;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest
class TradeRepositoryQueryTest {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    public void queryTrades()  {

        Sort sort = Sort.by(Sort.Direction.DESC, "ts","id");

        List<Tick> tickList = tradeRepository.findAll(sort);
        LOG.info(tickList.toString());
        Assertions.assertFalse(tickList.isEmpty());

    }

}