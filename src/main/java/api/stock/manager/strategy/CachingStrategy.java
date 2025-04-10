package api.stock.manager.strategy;

import api.stock.manager.adapter.PriceHandler;
import api.stock.manager.strategy.cache.CachableData;
import api.stock.manager.strategy.cache.CacheInterface;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CachingStrategy implements PriceRetrievalStrategy {

    private final CacheInterface m_cache;
    private final Integer m_expiration; // in seconds
    private PriceHandler m_priceHandler;

    public CachingStrategy(CacheInterface cache, Integer seconds) {
        m_cache = cache;
        m_expiration = seconds;
    }

    @Override
    public String getDescription() {
        return "This is an in-memory caching strategy, price will be retrieved " +
                "from local memory, if applicable. Cache will be updated after set expiration time.";
    }

    @Override
    public void setAdapter(PriceHandler adapter) {
        m_priceHandler = adapter;
    }

    @Override
    public BigDecimal getPrice(String ticker) throws IOException {
        BigDecimal price;
        CachableData data = m_cache.getData(ticker);
        if (data != null && checkIfWithinExpiration(data.getLastUpdated())) {
            price = data.getPrice();
        } else {
            price = m_priceHandler.getPrice(ticker);
            data = new CachableData(ticker, price, Instant.now());
            m_cache.addData(data);
        }
        return price;
    }


    @Override
    public Map<String, BigDecimal> getPrice(List<String> tickers) throws IOException {
        Map<String, BigDecimal> result = new HashMap<>();

        for (String ticker : tickers) {
            CachableData data = m_cache.getData(ticker);
            if (data != null && checkIfWithinExpiration(data.getLastUpdated())) {
                result.put(ticker, data.getPrice());
            } else {
                break;
            }
        }

        // if we found all tickers, return the result.
        // if we are missing even just 1, its more efficient to fetch the data for the whole batch
        if (result.size() == tickers.size()) {
            System.out.println("CachingStrategy::getPrice(batch) Returning result from Cache");
            return result;
        }

        System.out.println("CachingStrategy::getPrice(batch) cache miss, calling price handler");
        result = m_priceHandler.getPrice(tickers);
        m_cache.addData(result);
        return result;
    }

    private boolean checkIfWithinExpiration(Instant instant) {
        Instant now = Instant.now();
        Duration duration = Duration.between(instant, now);
        return Math.abs(duration.toSeconds()) <= m_expiration;

    }
}
