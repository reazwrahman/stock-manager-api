package api.stock.manager.decorator;

public class Runner {
    public static void main(String[] args) {
        System.out.println("--- Testing Base Stock Class ---");
        Stock baseStock = new Stock("AAPL", 2.00, 200.12);
        System.out.println("Ticker: " + baseStock.getTicker());
        System.out.println("Cost per stock: " + baseStock.getCost());

        System.out.println("--- Testing Price Decorator ---");
        PriceDecorator priceDecorator = new PriceDecorator(baseStock, 140.64);
        System.out.println("Return Rate: " + priceDecorator.returnRate);


        System.out.println("--- Testing Price + Industry Decorator ---");
        IndustryDecorator industryDecorator = new IndustryDecorator(priceDecorator,
                "Tech", "Hardware");
        System.out.println("Gain : " + industryDecorator.gain);
        System.out.println("Primary Industry: " + industryDecorator.primaryIndusty);

        System.out.println("--- Testing Price + Industry + Dividend Decorator ---");
        DividendDecorator dividendDecorator = new DividendDecorator(industryDecorator, 45.78);
        System.out.println("Primary Industry: " + dividendDecorator.seconda);

    }
}
