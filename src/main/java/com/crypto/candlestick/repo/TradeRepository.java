package com.crypto.candlestick.repo;

import com.crypto.candlestick.domain.Tick;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Tick,Long> {
}
