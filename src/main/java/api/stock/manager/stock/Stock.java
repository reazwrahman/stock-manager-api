package api.stock.manager.stock;

import lombok.Data;

@Data
public class Stock {
    protected String ticker;
    protected Double quantity;
    protected Double totalCost;
    protected Double cost;


    public Stock(){}
    public Stock(String ticker, Double quantity, Double totalCost) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.totalCost =  totalCost;
        findCostPerStock();
    }

    protected void findCostPerStock(){
        this.cost = (this.totalCost) / this.quantity;
    }
}