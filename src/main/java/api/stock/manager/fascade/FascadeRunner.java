package api.stock.manager.fascade;

import api.stock.manager.adapter.YahooWebAdapter;
import api.stock.manager.strategy.CachingStrategy;
import api.stock.manager.strategy.cache.CachableData;
import api.stock.manager.strategy.cache.CacheInterface;
import api.stock.manager.strategy.cache.SimpleCache;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class FascadeRunner {
    public static void main(String[] args) {
        testCacheHelper();
        testConcurrencyManager();

    }

    public static void testCacheHelper() {
        System.out.println("TESTING CACHE HELPER -------");
        CacheInterface cache = new SimpleCache();
        cache.addData(new CachableData("TSLA", new BigDecimal(123), Instant.now()));
        cache.addData(new CachableData("MSFT", new BigDecimal(123), Instant.now()));
        cache.addData(new CachableData("BA", new BigDecimal(123), Instant.MIN));

        CacheHelper cacheHelper = new CacheHelper(cache, 3);
        List<String> stocks = new ArrayList<>();
        stocks.add("TSLA");
        stocks.add("MSFT");
        stocks.add("BA");
        stocks.add("LMT");
        System.out.println("Generate Missing List: Expecting [BA, LMT] : -----");
        System.out.println(cacheHelper.generateMissingList(stocks));
        System.out.println("Build Partial Result: Expecting [TSLA, MSFT] data in the map : -------");
        System.out.println(cacheHelper.buildPartialResult(stocks));
        System.out.println();
    }

    public static void testConcurrencyManager() {
        System.out.println("----- Testing concurrency Manager ---- ");

        List<String> stockTickers = List.of("AAPL", "GOOGL", "MSFT", "AMZN", "TSLA", "BA", "NOC",
                "LMT", "COF", "RTX", "TSM", "SONY", "BAC");
        List<List<String>> batches = new ArrayList<>();
        int counter = 0;
        List<String> temp = new ArrayList<>();
        for (String ticker : stockTickers) {
            if (counter >= 5) {
                batches.add(temp);
                temp = new ArrayList<>();
                counter = 0;
            }
            temp.add(ticker);
            counter += 1;
        }
        batches.add(temp);
        System.out.println(batches);

        ConcurrencyManager manager = new ConcurrencyManager(new CachingStrategy(new SimpleCache(), 300), new YahooWebAdapter());
        System.out.println(manager.getPriceMultiStock(batches));
    }

}
