package api.stock.manager.fascade;

import api.stock.manager.stock.Stock;
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

    }

    public static void testCacheHelper(){
        System.out.println("TESTING CACHE HELPER -------");
        CacheInterface cache = new SimpleCache();
        cache.addData(new CachableData("TSLA", new BigDecimal(123), Instant.now()));
        cache.addData(new CachableData("MSFT", new BigDecimal(123), Instant.now()));
        cache.addData(new CachableData("BA", new BigDecimal(123), Instant.MIN));

        CacheHelper cacheHelper = new CacheHelper(cache, 3);
        List<String> stocks = new ArrayList<>();
        stocks.add("TSLA"); stocks.add("MSFT"); stocks.add("BA"); stocks.add("LMT");
        System.out.println("Generate Missing List: Expecting [BA, LMT] : -----");
        System.out.println(cacheHelper.generateMissingList(stocks));
        System.out.println("Build Partial Result: Expecting [TSLA, MSFT] data in the map : -------");
        System.out.println(cacheHelper.buildPartialResult(stocks));
        System.out.println();
    }
}
