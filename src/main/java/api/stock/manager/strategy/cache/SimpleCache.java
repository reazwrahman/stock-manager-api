package api.stock.manager.strategy.cache;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component("simpleCache")
public class SimpleCache implements CacheInterface {
    private final Map<String, CachableData> m_cache = new ConcurrentHashMap<>();

    @Override
    public void addData(CachableData data) {
        m_cache.put(data.ticker, data);
    }

    @Override
    public void removeData(String ticker) {
        m_cache.remove(ticker);
    }

    @Override
    public CachableData getData(String ticker) {
        if (m_cache.containsKey(ticker)) {
            return m_cache.get(ticker);
        }
        return null;
    }

    @Override
    public boolean exists(String ticker) {
        return m_cache.containsKey(ticker);
    }

    @Override
    public void addData(Map<String, BigDecimal> batch) {
        for (String key : batch.keySet()) {
            m_cache.put(key, new CachableData(key, batch.get(key), Instant.now()));
        }
    }
}
