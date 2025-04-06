package api.stock.manager.decorator;

import java.math.BigDecimal;

public class DividendDecorator extends AbstractDecorator{

    BigDecimal totalDividend;
    BigDecimal dividend;

    public DividendDecorator(Stock base, Double totalDividend) {
        super(base);
        this.totalDividend = BigDecimal.valueOf(totalDividend);
        this.dividend = this.totalDividend.divide(new BigDecimal(this.quantity));
    }
}
