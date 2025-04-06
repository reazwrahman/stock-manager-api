package api.stock.manager;

import api.stock.manager.decorator.Stock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RequestController {
    @PostMapping("/sort-by-return-rate")
    public ResponseEntity<String> sortByReturnRate(@RequestBody List<Stock> stocks) {
        for (Stock stock : stocks) {
            System.out.println("Received stock: " + stock.getTicker() + " @ $" + stock.getQuantity());
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Stocks received successfully.");
    }
}
