package api.stock.manager.stock;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockWithPrice extends Stock{
    public BigDecimal price;
    public BigDecimal totalGain;
    public BigDecimal gain;
    public BigDecimal totalValue;
    public BigDecimal returnRate;

    public StockWithPrice(Stock baseStock, Double price) {
        super(baseStock.ticker, baseStock.quantity, baseStock.totalCost);
        this.price = BigDecimal.valueOf(price);
        calculateGains();
    }

    private void calculateGains() {
        this.totalValue = this.price.multiply(BigDecimal.valueOf(this.quantity));
        this.totalGain = this.totalValue.subtract(BigDecimal.valueOf(this.totalCost));
        this.gain = this.price.subtract(BigDecimal.valueOf(this.cost));

        this.returnRate = this.totalValue.divide(BigDecimal.valueOf(this.totalCost), 2);
        this.returnRate = convertToPercentage(this.returnRate);
    }

    private BigDecimal convertToPercentage(BigDecimal val) {
        return val.subtract(BigDecimal.ONE).multiply(new BigDecimal(100));

    }

}
