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


    public Stock() {
    }

    public Stock(String ticker, BigDecimal quantity, BigDecimal totalCost) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.totalCost = totalCost;
        findCostPerStock();
    }

    protected void findCostPerStock() {
        this.cost = (this.totalCost).divide(this.quantity, 2, RoundingMode.HALF_UP);
    }
}