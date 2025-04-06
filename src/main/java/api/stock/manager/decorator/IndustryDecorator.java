package api.stock.manager.decorator;

public class IndustryDecorator extends AbstractDecorator{

    public String primaryIndusty;
    public String secondaryIndustry;

    public IndustryDecorator(Stock base, String primaryIndustry, String secondaryIndustry) {
        super(base);
        this.primaryIndusty = primaryIndustry;
        this.secondaryIndustry = secondaryIndustry;
    }

}
