package api.stock.manager.strategy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PriceRetrievalStrategy {
    String getDescription();

    void setParameters(CacheStrategyParameters parameters);

    BigDecimal getPrice(String ticker) throws Exception;

    Map<String, BigDecimal> getPrice(List<String> tickers) throws Exception;
}
