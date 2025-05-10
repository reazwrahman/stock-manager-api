package api.stock.manager.adapter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PriceHandler {
    BigDecimal getPrice(String ticker) throws Exception;

    Map<String, BigDecimal> getPrice(List<String> tickers) throws Exception;

    String getDescription();
}
