package api.stock.manager.strategy.cache;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class CachableData {
    String ticker;
    BigDecimal price;
    Instant lastUpdated;

    public CachableData(String ticker, BigDecimal price, Instant lastUpdated) {
        this.ticker = ticker;
        this.price = price;
        this.lastUpdated = lastUpdated;
    }
}
