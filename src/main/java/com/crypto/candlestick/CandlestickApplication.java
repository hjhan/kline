package com.crypto.candlestick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CandlestickApplication {

	public static void main(String[] args) {
		SpringApplication.run(CandlestickApplication.class, args);
	}

}
