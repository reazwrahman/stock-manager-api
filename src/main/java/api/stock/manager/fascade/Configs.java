package api.stock.manager.fascade;

import api.stock.manager.adapter.PriceHandler;
import api.stock.manager.strategy.CacheStrategyParameters;
import api.stock.manager.strategy.PriceRetrievalStrategy;
import api.stock.manager.strategy.cache.CacheInterface;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Dynamically generate the correct implementation of the interfaces
 * based on user defined inputs in application.properties
 */
@Service
public class Configs {
    // Spring managed factories
    private final Map<String, CacheInterface> m_cacheMap;
    private final Map<String, PriceRetrievalStrategy> m_cacheStrategyMap;
    private final Map<String, PriceHandler> m_adapterMap;

    @Value("${stock.batch.size}")
    Integer m_batchSize; // number of stocks per API call
    @Value("${cache.ttl}")
    Integer m_cacheTTL;
    // task delegators
    PriceHandler m_adapter;
    CacheInterface m_cache;
    PriceRetrievalStrategy m_cachingStrategy;
    @Value("${cache.type}")
    private String m_cacheType;
    @Value("${cache.strategy}")
    private String m_cacheStrategyType;
    @Value("${adapter.type}")
    private String m_adapterType;

    @Autowired
    public Configs(Map<String, CacheInterface> cacheMap,
                   Map<String, PriceRetrievalStrategy> cacheStrategyMap,
                   Map<String, PriceHandler> adapterMap) {
        m_cacheMap = cacheMap;
        m_cacheStrategyMap = cacheStrategyMap;
        m_adapterMap = adapterMap;
    }

    @PostConstruct
    public void init() {
        m_cache = m_cacheMap.get(m_cacheType);
        m_adapter = m_adapterMap.get(m_adapterType);
        m_cachingStrategy = m_cacheStrategyMap.get("cachingStrategy");
        m_cachingStrategy.setParameters(new CacheStrategyParameters(m_adapter, m_cache, m_cacheTTL));
    }

}
