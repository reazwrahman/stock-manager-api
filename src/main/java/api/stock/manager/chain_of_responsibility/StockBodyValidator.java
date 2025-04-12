package api.stock.manager.chain_of_responsibility;

import api.stock.manager.stock.RequestModel;
import api.stock.manager.stock.Stock;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StockBodyValidator implements RequestValidator {
    @Override
    public ErrorResponse validate(RequestModel request) {

        try {
            for (Stock stock : request.getStocks()) {
                if (stock.getTicker().isBlank() || stock.getTicker() == null ||
                        stock.getQuantity().compareTo(BigDecimal.ZERO) == 0 || stock.getQuantity() == null ||
                        stock.getTotalCost().compareTo(BigDecimal.ZERO) == 0 || stock.getTotalCost() == null) {

                    return new ErrorResponse(true, "Each stock must have a valid ticker, quantity and total cost");
                }
            }
        } catch (Exception ex) {
            return new ErrorResponse(true, "Unable to parse stocks from the request");
        }

        return new ErrorResponse(false, NO_ERROR_MESSAGE);
    }
}
