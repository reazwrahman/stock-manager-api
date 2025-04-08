package api.stock.manager.fascade;

import api.stock.manager.adapter.YahooWebAdapter;
import api.stock.manager.strategy.NoCacheStrategy;
import api.stock.manager.strategy.PriceRetrievalStrategy;
import lombok.Data;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Data
class Tuple{
    String ticker;
    BigDecimal price;
    public Tuple(String ticker, BigDecimal price){
        this.ticker = ticker;
        this.price = price;
    }
}

public class ConcurrencyManager {
    public static void main(String[] args) throws Exception {
        PriceRetrievalStrategy noCache = new NoCacheStrategy();
        noCache.setAdapter(new YahooWebAdapter());

        List<String> stockTickers = List.of("AAPL", "GOOGL", "MSFT", "AMZN", "TSLA", "BA", "NOC",
                "LMT", "COF", "RTX", "TSM");
        List<List<String>> batches = new ArrayList<>();
        int counter = 0;
        List<String> temp = new ArrayList<>();
        for (String ticker: stockTickers) {
            if (counter >=5) {
                batches.add(temp);
                temp = new ArrayList<>();
                counter = 0;
            }
            temp.add(ticker);
            counter +=1;
        }
        batches.add(temp);
        System.out.println(batches);

        ExecutorService executor = Executors.newFixedThreadPool(stockTickers.size());

        // Store all futures
        List<CompletableFuture<Map<String, BigDecimal>>> futures = new ArrayList<>();

        for (List<String> batch : batches) {
            CompletableFuture<Map<String, BigDecimal>> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return noCache.getPrice(batch); // simulate async task
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

        results.forEach(System.out::println);

        executor.shutdown();
    }


}
