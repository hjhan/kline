package com.crypto.candlestick;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootTest
public class DBTests {

    @Autowired
    @Qualifier("myDbcp2DataSource")
    private DataSource dataSource;

    @Test
    public void springJdbcTemplateTest(){


    }
}

