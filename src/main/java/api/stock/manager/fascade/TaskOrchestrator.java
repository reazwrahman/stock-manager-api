package api.stock.manager.fascade;

import api.stock.manager.adapter.PriceHandler;
import api.stock.manager.adapter.YahooWebAdapter;
import api.stock.manager.stock.Stock;
import api.stock.manager.stock.StockWithPrice;
import api.stock.manager.strategy.CachingStrategy;
import api.stock.manager.strategy.cache.CacheInterface;
import api.stock.manager.strategy.cache.SimpleCache;
import org.springframework.cache.Cache;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * - Receive stock list from request controller
 * - Get the available ones from cache
 * - For the remaining ones, split into batches
 *   - Call concurrency manager to get results
 * - Use the result to build a list of enriched stocks
 * - sort the stocks
 * - Create a response object
 * - Return the response model back to the controller
 */
public class TaskOrchestrator {

    // TODO: move these to a config file
    // TODO: make object creations more dynamic based on configs
    private final Integer m_batchSize = 5; // number of stocks per API call
    private final Integer m_cacheTTL = 300; //seconds
    private final CacheInterface m_cache = new SimpleCache();
    private final CachingStrategy m_cachingStrategy = new CachingStrategy(m_cache, m_cacheTTL);
    private final CacheHelper m_cacheHelper = new CacheHelper(m_cache, m_cacheTTL);
    private final PriceHandler m_adapter = new YahooWebAdapter();
    private final ConcurrencyManager m_concurrencyManager = new ConcurrencyManager(m_cachingStrategy, m_adapter);

    public List<StockWithPrice> orchestrate(List<Stock> stocks) {
        List<String> tickers = stocks.stream()
                .map(Stock::getTicker)
                .collect(Collectors.toList());

        Map<String, BigDecimal> partialResult = m_cacheHelper.buildPartialResult(tickers);

        List<List<String>> targetBatches = splitIntoBatches(m_cacheHelper.generateMissingList(tickers));
        Map<String, BigDecimal> remainingResult = m_concurrencyManager.getPriceMultiStock(targetBatches);

        Map<String, BigDecimal> fullResult = new HashMap<>(partialResult);
        fullResult.putAll(remainingResult);

        List<StockWithPrice> stocksWithPrice = aggregateStocksWithPrice(stocks, fullResult);
        Collections.sort(stocksWithPrice, Comparator.comparing(StockWithPrice::getReturnRate).reversed());

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

    private List<StockWithPrice> aggregateStocksWithPrice(List<Stock> stocks, Map<String, BigDecimal> result){
        List<StockWithPrice> enrichedStocks = new ArrayList<>();
        for (Stock stock: stocks) {
            enrichedStocks.add(new StockWithPrice(stock, result.get(stock.getTicker())));
        }
        return enrichedStocks;
    }
}


