package api.stock.manager.adapter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class YahooWebAdapter implements PriceHandler{

    @Override
    public BigDecimal getPrice(String ticker) {
        return null;
    }

    @Override
    public Map<String, BigDecimal> getPrice(List<String> tickers) {
        return null;
    }

    private void scrapePage(){

    }

    private void buildURL(String ticker){

    }

    private void buildURL(List<String> tickers){

    }
}
