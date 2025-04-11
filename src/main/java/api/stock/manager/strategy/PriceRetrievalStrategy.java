package api.stock.manager.strategy;

import api.stock.manager.adapter.PriceHandler;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PriceRetrievalStrategy {
    String getDescription();

    void setParameters(CacheStrategyParameters parameters);

    BigDecimal getPrice(String ticker) throws IOException;

    Map<String, BigDecimal> getPrice(List<String> tickers) throws IOException;
}
