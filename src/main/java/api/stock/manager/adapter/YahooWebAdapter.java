package api.stock.manager.adapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YahooWebAdapter implements PriceHandler {

    private String m_baseURL = "https://finance.yahoo.com/quotes/";

    @Override
    public BigDecimal getPrice(String ticker) throws IOException {
        var mappedData = scrapePage(buildURL(ticker));
        if (mappedData.containsKey(ticker)) {
            return mappedData.get(ticker);
        }
        return null;
    }

    @Override
    public Map<String, BigDecimal> getPrice(List<String> tickers) throws IOException {
        return scrapePage(buildURL(tickers));
    }

    @Override
    public String getDescription() {
        return "This is an adapter for web scraping Yahoo's stock price page, base URL: " + m_baseURL;
    }

    private Map<String, BigDecimal> scrapePage(String url) throws IOException {
        Map<String, BigDecimal> mappedData = new HashMap<>();
        try {
            // Fetch the HTML content from the URL
            Document doc = Jsoup.connect(url).get();

            // Select the rows where stock data is present
            Elements rows = doc.select("tr");

            for (Element row : rows) {
                // Extract ticker and price from specific table cells
                Elements cols = row.select("td");
                if (cols.size() > 1) {
                    String ticker = cols.get(0).text();

                    String price = cols.get(1).text().split(" ")[0];
                    mappedData.put(ticker, new BigDecimal(price));
                }
            }
        } catch (IOException e) {
            throw new IOException("YahooWebAdapter::scrapePage failed to get data" + url);
        }
        return mappedData;
    }

    private String buildURL(String ticker) {
        return m_baseURL + ticker;

    }

    public String buildURL(List<String> tickers) {
        StringBuilder extension = new StringBuilder();
        for (int i = 0; i < tickers.size() - 1; i++) {
            extension.append(tickers.get(i));
            extension.append(",");
        }
        extension.append(tickers.getLast());
        extension.append("/");
        return m_baseURL + extension;
    }
}
