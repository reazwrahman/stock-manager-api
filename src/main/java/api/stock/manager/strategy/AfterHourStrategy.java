package api.stock.manager.strategy;

import api.stock.manager.adapter.PriceHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component("afterHourStrategy")
public class AfterHourStrategy implements PriceRetrievalStrategy {
    private PriceHandler m_adapter;

    @Override
    public String getDescription() {
        return "This is the AfterHourStrategy, that will only invoke downstream API if the call is made during " +
                "business hour, otherwise will resort to in-memory cache";
    }

    @Override
    public void setParameters(CacheStrategyParameters parameters) {
        // TODO
    }

    @Override
    public BigDecimal getPrice(String ticker) throws IOException {
        return null;
    }

    @Override
    public Map<String, BigDecimal> getPrice(List<String> tickers) throws IOException {
        return null;
    }
}
