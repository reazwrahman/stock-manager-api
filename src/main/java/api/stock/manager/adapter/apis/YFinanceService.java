package api.stock.manager.adapter.apis;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class YFinanceService implements APIHandler{

    @Override
    public void prepareRequestBody() {

    }

    @Override
    public void sendRequest() {

    }

    @Override
    public void parseResponse() {

    }

    @Override
    public BigDecimal getPrice(String ticker) {
        return null;
    }

    @Override
    public Map<String, BigDecimal> getPrice(List<String> tickers) {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
