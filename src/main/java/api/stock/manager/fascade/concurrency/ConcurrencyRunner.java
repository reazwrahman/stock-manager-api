package api.stock.manager.fascade.concurrency;

import api.stock.manager.adapter.YahooWebAdapter;
import api.stock.manager.strategy.CachingStrategy;
import api.stock.manager.strategy.PriceRetrievalStrategy;
import api.stock.manager.strategy.cache.SimpleCache;
import lombok.Data;

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

@Data
class Tuple{
    String ticker;
    BigDecimal price;
    public Tuple(String ticker, BigDecimal price){
        this.ticker = ticker;
        this.price = price;
    }
}

public class ConcurrencyRunner {
    public static void main(String[] args) throws Exception {
        List<String> stockTickers = List.of("AAPL", "GOOGL", "MSFT", "AMZN", "TSLA", "BA", "NOC",
                "LMT", "COF", "RTX", "TSM", "SONY", "BOFA");
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

        ConcurrencyManager manager = new ConcurrencyManager(new CachingStrategy(new SimpleCache(), 300), new YahooWebAdapter());
        System.out.println(manager.getPriceMultiStock(batches));
    }


}
