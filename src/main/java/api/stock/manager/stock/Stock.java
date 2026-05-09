package api.stock.manager.stock;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class Stock {
    protected String ticker;
    protected BigDecimal quantity;
    protected BigDecimal totalCost;
    protected BigDecimal cost;
    protected String source;


    public Stock() {
    }

    public Stock(String ticker, BigDecimal quantity, BigDecimal totalCost, String source) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.totalCost = totalCost;
        this.source = source;
        findCostPerStock();
    }

    protected void findCostPerStock() {
        this.cost = (this.totalCost).divide(this.quantity, 2, RoundingMode.HALF_UP);
    }
}