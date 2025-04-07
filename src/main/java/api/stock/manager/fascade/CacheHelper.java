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

    Map<String, BigDecimal> buildPartialResult(ArrayList<Stock> stocks){
        Map<String, BigDecimal> result = new HashMap<>();
        List<Stock> remainder = new ArrayList<>(stocks);
        remainder.removeAll(generateMissingList(stocks));
        for (Stock stock: remainder) {
            result.put(stock.getTicker(), m_cache.getData(stock.getTicker()).getPrice());
        }
        return result;
    }

    List<Stock> generateMissingList(List<Stock> stocks){
        List<Stock> missing = new ArrayList<>();
        for (Stock stock: stocks) {
            CachableData data = m_cache.getData(stock.getTicker());
            if (data == null || checkIfExpired(data.getLastUpdated(), m_expiration)) {
                m_cache.removeData(data);
                missing.add(stock);
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
