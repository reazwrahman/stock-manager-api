package api.stock.manager;

import api.stock.manager.fascade.TaskOrchestrator;
import api.stock.manager.stock.Stock;
import api.stock.manager.stock.StockWithPrice;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@RestController
public class RequestController {

    TaskOrchestrator orchestrator = new TaskOrchestrator();

    @PostMapping("/sort-by-return-rate")
    public ResponseEntity<List<StockWithPrice>> sortByReturnRate(@RequestBody List<Stock> stocks) {

        // example of fascade pattern: orchestrator hides all the complexity
        return ResponseEntity.ok(orchestrator.orchestrate(stocks));
    }
}
