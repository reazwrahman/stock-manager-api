package api.stock.manager.decorator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceDecorator extends AbstractDecorator {

    public BigDecimal price;
    public BigDecimal totalGain;
    public BigDecimal gain;
    public BigDecimal totalValue;
    public BigDecimal returnRate;

    public PriceDecorator(Stock baseStock, Double price) {
        super(baseStock);
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