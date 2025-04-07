package api.stock.manager.strategy;

import api.stock.manager.adapter.PriceHandler;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class NoCacheStrategy implements PriceRetrievalStrategy {

    private PriceHandler m_priceHandler;

    @Override
    public String getDescription() {
        return "This is a simple strategy that acts as a proxy between the client and price retrieving service, " +
                "no caching is implemented for this strategy";
    }

    @Override
    public void setAdapter(PriceHandler adapter) {
        m_priceHandler = adapter;
    }

    @Override
    public BigDecimal getPrice(String ticker) throws IOException {
        return m_priceHandler.getPrice(ticker);
    }

    @Override
    public Map<String, BigDecimal> getPrice(List<String> tickers) throws IOException {
        return m_priceHandler.getPrice(tickers);
    }
}
