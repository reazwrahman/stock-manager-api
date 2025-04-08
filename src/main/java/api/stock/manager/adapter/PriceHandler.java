package api.stock.manager.adapter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PriceHandler {
    BigDecimal getPrice(String ticker) throws IOException;

    Map<String, BigDecimal> getPrice(List<String> tickers) throws IOException;

    String getDescription();
}
