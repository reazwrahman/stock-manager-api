package api.stock.manager.adapter;

import api.stock.manager.adapter.apis.StockPulseService;
import api.stock.manager.adapter.apis.YFinanceService;
import api.stock.manager.stock.Stock;
import api.stock.manager.stock.StockWithPrice;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AdapterRunner {
    public static void main(String[] args) throws IOException {
        System.out.println("---- Testing Adapter Runner -----");
        testAdapterPattern(new YahooWebAdapter());
        testAdapterPattern(new YFinanceService());
        testAdapterPattern(new StockPulseService());

        testWebScraper();

    }

    public static void testAdapterPattern(PriceHandler handler) throws IOException {
        System.out.println(handler.getDescription());
        Stock stock = new Stock("AAPL", new BigDecimal(50.00), new BigDecimal(621.21));
        StockWithPrice enhancedStock = new StockWithPrice(stock, handler.getPrice(stock.getTicker()));
        System.out.println("Return rate: " + enhancedStock.getReturnRate());
        System.out.println();
    }

    public static void testWebScraper() throws IOException {
        System.out.println("---- Testing Yahoo Web Scraper -----");
        YahooWebAdapter scraper = new YahooWebAdapter();
        List<String> stocks = new ArrayList<>();
        stocks.add("BA");
        stocks.add("TSLA");
        stocks.add("MSFT");
        stocks.add("NVDA");
        System.out.print("Tesing multi stock: ");
        System.out.println(scraper.getPrice(stocks));
        System.out.print("Testing single stock: ");
        System.out.println(scraper.getPrice("NOC"));

    }


}
