package api.stock.manager.stock;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class StockWithPrice extends Stock {
    public BigDecimal price;
    public BigDecimal totalGain;
    public BigDecimal gain;
    public BigDecimal totalValue;
    public BigDecimal returnRate;

    public StockWithPrice(Stock baseStock, BigDecimal price) {
        super(baseStock.ticker, baseStock.quantity, baseStock.totalCost);
        this.price = price;
        calculateGains();
    }

    private void calculateGains() {
        this.totalValue = this.price.multiply(this.quantity);
        this.totalGain = this.totalValue.subtract(this.totalCost);
        this.gain = this.price.subtract(this.cost);

        this.returnRate = this.totalValue.divide(this.totalCost, RoundingMode.HALF_UP);
        this.returnRate = convertToPercentage(this.returnRate);
    }

    private BigDecimal convertToPercentage(BigDecimal val) {
        return val.subtract(BigDecimal.ONE).multiply(new BigDecimal(100));

    }

}
