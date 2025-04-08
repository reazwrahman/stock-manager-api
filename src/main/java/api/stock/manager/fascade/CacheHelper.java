package api.stock.manager.fascade;

import api.stock.manager.stock.Stock;
import api.stock.manager.strategy.cache.CachableData;
import api.stock.manager.strategy.cache.CacheInterface;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheHelper {

    private CacheInterface m_cache;
    private Integer m_expiration;

    public CacheHelper(CacheInterface cache, Integer ttlSeconds){
        m_cache = cache;
        m_expiration = ttlSeconds;
    }

    Map<String, BigDecimal> buildPartialResult(List<String> stocks){
        Map<String, BigDecimal> result = new HashMap<>();
        List<String> remainder = new ArrayList<>(stocks);
        remainder.removeAll(generateMissingList(stocks));
        for (String ticker: remainder) {
            result.put(ticker, m_cache.getData(ticker).getPrice());
        }
        return result;
    }

    List<String> generateMissingList(List<String> tickers){
        List<String > missing = new ArrayList<>();
        for (String ticker: tickers) {
            CachableData data = m_cache.getData(ticker);
            if (data == null || checkIfExpired(data.getLastUpdated(), m_expiration)) {
                m_cache.removeData(ticker);
                missing.add(ticker);
            }
        }
        return missing;
    }

    private boolean checkIfExpired(Instant instant, Integer ttl) {
        Instant now = Instant.now();
        Duration duration = Duration.between(instant, now);
        return Math.abs(duration.toSeconds()) > ttl;
    }
}
