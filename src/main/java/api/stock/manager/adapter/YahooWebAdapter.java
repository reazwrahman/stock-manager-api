package api.stock.manager.adapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("yahooWebAdapter")
public class YahooWebAdapter implements PriceHandler {

    private String m_baseURL = "https://finance.yahoo.com/quotes/";

    @Override
    public BigDecimal getPrice(String ticker) throws Exception {
        var mappedData = scrapePage(buildURL(ticker));
        if (mappedData.containsKey(ticker)) {
            return mappedData.get(ticker);
        }
        return null;
    }

    @Override
    public Map<String, BigDecimal> getPrice(List<String> tickers) throws Exception {
        return scrapePage(buildURL(tickers));
    }

    @Override
    public String getDescription() {
        return "This is an adapter for web scraping Yahoo's stock price page, base URL: " + m_baseURL;
    }

    private Map<String, BigDecimal> scrapePage(String plainUrl) throws Exception {
        Map<String, BigDecimal> mappedData = new HashMap<>();
        try {
            // parse with Jsoup
            Document doc = Jsoup.parse(makeApiRequest(plainUrl));

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
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("YahooWebAdapter::scrapePage failed to get data" + plainUrl);
        }
        return mappedData;
    }

    private String makeApiRequest(String plainUrl) throws Exception {
        try {
            StringBuilder content = null;
            // Fetch the HTML content from the URL
            URL url = new URL(plainUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0...");
            conn.setRequestProperty("Accept", "text/html");
            conn.setRequestProperty("Connection", "keep-alive");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();
            return content.toString();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
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
