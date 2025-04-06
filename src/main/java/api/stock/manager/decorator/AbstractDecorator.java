package api.stock.manager.decorator;


public abstract class AbstractDecorator extends Stock {
    protected Stock baseStock;

    public AbstractDecorator(Stock base){
        super(base.ticker, base.quantity, base.totalCost);
        this.baseStock = base;
    }
}
