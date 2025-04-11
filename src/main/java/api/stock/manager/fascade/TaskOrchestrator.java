package api.stock.manager.fascade;

import api.stock.manager.adapter.PriceHandler;
import api.stock.manager.stock.Stock;
import api.stock.manager.stock.StockWithPrice;
import api.stock.manager.strategy.CacheStrategyParameters;
import api.stock.manager.strategy.CachingStrategy;
import api.stock.manager.strategy.PriceRetrievalStrategy;
import api.stock.manager.strategy.cache.CacheInterface;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * - Receive stock list from request controller
 * - Get the available ones from cache
 * - For the remaining ones, split into batches
 * - Call concurrency manager to call downstream API
 * - Use the result to build a list of enriched stocks
 * - sort the stocks
 * - Create a response object
 * - Return the response model back to the controller
 */

@Service
public class TaskOrchestrator {

    @Value("${stock.batch.size}")
    private Integer m_batchSize; // number of stocks per API call

    @Value("${cache.ttl}")
    private Integer m_cacheTTL;

    @Value("${cache.type}")
    private String m_cacheType;

    @Value("${cache.strategy}")
    private String m_cacheStrategyType;

    @Value("${adapter.type}")
    private String m_adapterType;

    // task delegators
    private PriceHandler m_adapter;
    private CacheInterface m_cache;
    private PriceRetrievalStrategy m_cachingStrategy;
    private ConcurrencyManager m_concurrencyManager;
    private CacheHelper m_cacheHelper;

    private final Map<String, Comparator<StockWithPrice>> m_comparatorMap = new HashMap<>();

    // Spring managed factories
    private final Map<String, CacheInterface> m_cacheMap;
    private final Map<String, PriceRetrievalStrategy> m_cacheStrategyMap;
    private final Map<String, PriceHandler> m_adapterMap;

    @Autowired
    public TaskOrchestrator(Map<String, CacheInterface> cacheMap,
                            Map<String, PriceRetrievalStrategy> cacheStrategyMap,
                            Map<String, PriceHandler> adapterMap) {
        m_cacheMap = cacheMap;
        m_cacheStrategyMap = cacheStrategyMap;
        m_adapterMap = adapterMap;

        initializeComparators();
    }

    @PostConstruct
    public void init() {
        m_cache = m_cacheMap.get(m_cacheType);
        m_adapter = m_adapterMap.get(m_adapterType);
        m_cachingStrategy = m_cacheStrategyMap.get("cachingStrategy");
        m_cachingStrategy.setParameters(new CacheStrategyParameters(m_adapter, m_cache, m_cacheTTL));
        m_concurrencyManager = new ConcurrencyManager(m_cachingStrategy, m_adapter);
        m_cacheHelper = new CacheHelper(m_cache, m_cacheTTL);
    }


    public void initializeComparators() {
        m_comparatorMap.put("/sort-by-return-rate", Comparator.comparing(StockWithPrice::getReturnRate).reversed());
        m_comparatorMap.put("/sort-by-total-gain", Comparator.comparing(StockWithPrice::getTotalGain).reversed());
    }

    public List<StockWithPrice> orchestrate(List<Stock> stocks, String sortingStrategy) {
        List<String> tickers = stocks.stream()
                .map(Stock::getTicker)
                .collect(Collectors.toList());

        Map<String, BigDecimal> partialResult = m_cacheHelper.buildPartialResult(tickers);

        List<List<String>> targetBatches = splitIntoBatches(m_cacheHelper.generateMissingList(tickers));
        Map<String, BigDecimal> remainingResult = m_concurrencyManager.getPriceMultiStock(targetBatches);

        Map<String, BigDecimal> fullResult = new HashMap<>(partialResult);
        fullResult.putAll(remainingResult);

        List<StockWithPrice> stocksWithPrice = aggregateStocksWithPrice(stocks, fullResult);
        Collections.sort(stocksWithPrice, generateComparator(sortingStrategy));
        return stocksWithPrice;
    }

    private List<List<String>> splitIntoBatches(List<String> tickers) {
        List<List<String>> batches = new ArrayList<>();
        int counter = 0;
        List<String> temp = new ArrayList<>();
        for (String ticker : tickers) {
            if (counter >= m_batchSize) {
                batches.add(temp);
                temp = new ArrayList<>();
                counter = 0;
            }
            temp.add(ticker);
            counter += 1;
        }
        batches.add(temp);
        return batches;
    }

    private List<StockWithPrice> aggregateStocksWithPrice(List<Stock> stocks, Map<String, BigDecimal> result) {
        List<StockWithPrice> enrichedStocks = new ArrayList<>();
        for (Stock stock : stocks) {
            enrichedStocks.add(new StockWithPrice(stock, result.get(stock.getTicker())));
        }
        return enrichedStocks;
    }

    private Comparator<StockWithPrice> generateComparator(String path) {
        if (m_comparatorMap.containsKey(path)) {
            return m_comparatorMap.get(path);
        } else {
            throw new RuntimeException("TaskOrchestrator::generateComparator invalid path");
        }
    }
}


