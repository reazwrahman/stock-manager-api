package api.stock.manager.fascade;

import api.stock.manager.adapter.PriceHandler;
import api.stock.manager.strategy.CachingStrategy;
import api.stock.manager.strategy.PriceRetrievalStrategy;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ConcurrencyManager {

    private PriceRetrievalStrategy m_cachingStrategy;
    private PriceHandler m_adapter;

    public ConcurrencyManager(PriceRetrievalStrategy cachingStrategy, PriceHandler adapter) {
        m_cachingStrategy = cachingStrategy;
        m_adapter = adapter;
        m_cachingStrategy.setAdapter(adapter);
    }

    public Map<String, BigDecimal> getPriceMultiStock(List<List<String>> stocks) {
        ExecutorService executor = Executors.newFixedThreadPool(stocks.size());

        // Store all futures
        List<CompletableFuture<Map<String, BigDecimal>>> futures = new ArrayList<>();

        for (List<String> batch : stocks) {
            CompletableFuture<Map<String, BigDecimal>> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return m_cachingStrategy.getPrice(batch); // simulate async task
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }, executor);

            futures.add(future);
        }

        // Wait for all to complete and collect results
        List<Map<String, BigDecimal>> results = futures.stream()
                .map(CompletableFuture::join) // .join doesn't throw checked exceptions
                .collect(Collectors.toList());

        executor.shutdown();

        return createFlatMap(results);

    }

    private Map<String, BigDecimal> createFlatMap(List<Map<String, BigDecimal>> results) {
        Map<String, BigDecimal> output = new HashMap<>();
        for (Map<String, BigDecimal> map : results) {
            for (String key : map.keySet()) {
                output.put(key, map.get(key));
            }
        }
        return output;
    }
}
