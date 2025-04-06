package api.stock.manager.adapter.apis;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class StockPulseService implements APIHandler{

    private String m_baseURL = "https://yahoo-finance127.p.rapidapi.com/multi-quote";

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
        return new BigDecimal("300.12");
    }

    @Override
    public Map<String, BigDecimal> getPrice(List<String> tickers) {
        return null;
    }

    @Override
    public String getDescription() {
        return "This is an adapter for calling StockPulse API for stock price, base URL: " + m_baseURL;

    }
}
