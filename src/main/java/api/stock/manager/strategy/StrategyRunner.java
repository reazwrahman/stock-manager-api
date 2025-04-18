package api.stock.manager.strategy;

import api.stock.manager.adapter.PriceHandler;
import api.stock.manager.adapter.YahooWebAdapter;
import api.stock.manager.strategy.cache.SimpleCache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StrategyRunner {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("---- STRATEGY PATTERN: TESTING SWITCHING STRATEGIES AT RUNTIME");
        PriceHandler adapter = new YahooWebAdapter();

        testStrategyPattern(new NoCacheStrategy(), adapter);
        var cachingStrategy = new CachingStrategy();
        cachingStrategy.setCache(new SimpleCache(), 5);
        testStrategyPattern(cachingStrategy, adapter);

        testStrategyPattern(new AfterHourStrategy(), adapter);
        System.out.println();
        testCachingStrategy();
    }

    public static void testStrategyPattern(PriceRetrievalStrategy strategy, PriceHandler adapter) {
        CacheStrategyParameters parameters = new CacheStrategyParameters(adapter, new SimpleCache(), 3);
        strategy.setParameters(parameters);
        System.out.println(strategy.getDescription());
    }

    public static void testCachingStrategy() throws IOException, InterruptedException {
        System.out.println("----- TESTING CACHING STRATEGY ------");
        PriceHandler adapter = new YahooWebAdapter();
        List<String> stocks = new ArrayList<>();
        stocks.add("BA");
        stocks.add("TSLA");
        stocks.add("MSFT");
        stocks.add("NVDA");
        stocks.add("LMT");
        CacheStrategyParameters parameters = new CacheStrategyParameters(adapter, new SimpleCache(), 2);
        PriceRetrievalStrategy cachingStrategy = new CachingStrategy();
        cachingStrategy.setParameters(parameters);
        System.out.println("Expecting a cache miss ----------- ");
        System.out.println(cachingStrategy.getPrice(stocks));
        System.out.println("Result should be from cache ----------- ");
        System.out.println(cachingStrategy.getPrice(stocks));
        System.out.println("TTL expired, expecting a cache miss ----------- ");
        Thread.sleep(3000); // wait 1 second more than ttl (time to live)
        System.out.println(cachingStrategy.getPrice(stocks));

    }
}
