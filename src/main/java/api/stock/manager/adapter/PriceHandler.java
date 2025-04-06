package api.stock.manager.adapter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PriceHandler {
    BigDecimal getPrice(String ticker);
    Map<String, BigDecimal> getPrice(List<String> tickers);
    String getDescription();
}
