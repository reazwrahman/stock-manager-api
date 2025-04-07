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
                "LMT", "COF", "RTX");
        ExecutorService executor = Executors.newFixedThreadPool(stockTickers.size());

        // Store all futures
        List<CompletableFuture<Tuple>> futures = new ArrayList<>();

        for (String ticker : stockTickers) {
            CompletableFuture<Tuple> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return new Tuple(ticker, noCache.getPrice(ticker)); // simulate async task
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }, executor);

            futures.add(future);
        }

        // Wait for all to complete and collect results
        List<Tuple> results = futures.stream()
                .map(CompletableFuture::join) // .join doesn't throw checked exceptions
                .collect(Collectors.toList());

        results.forEach(System.out::println);

        executor.shutdown();
    }


}
