package api.stock.manager;

import api.stock.manager.stock.Stock;
import api.stock.manager.stock.StockWithPrice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;

@RestController
public class RequestController {
    @PostMapping("/sort-by-return-rate")
    public ResponseEntity<String> sortByReturnRate(@RequestBody List<Stock> stocks) {
        List<StockWithPrice> priceAdded = new ArrayList<>();
        for (Stock stock : stocks) {
            priceAdded.add(new StockWithPrice(stock, 100.00));
        }

        for (StockWithPrice stock : priceAdded) {
            System.out.println("Received stock: " + stock.getTicker() + " @ " +
                    stock.getQuantity() + " @ $" + stock.getPrice());

        }

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Stocks received successfully.");
    }
}
