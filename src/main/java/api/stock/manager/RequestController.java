package api.stock.manager;

import api.stock.manager.fascade.TaskOrchestrator;
import api.stock.manager.stock.Stock;
import api.stock.manager.stock.StockWithPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class RequestController {

    // TODO: add a logger object, convert prints to logs in the codebase

    @Autowired
    TaskOrchestrator orchestrator;

    @PostMapping("/sort-by-return-rate")
    public ResponseEntity<List<StockWithPrice>> sortByReturnRate(@RequestBody List<Stock> stocks) {

        // example of facade pattern: orchestrator hides all the complexity
        return ResponseEntity.ok(orchestrator.orchestrate(stocks, "/sort-by-return-rate"));
    }

    @PostMapping("/sort-by-total-gain")
    public ResponseEntity<List<StockWithPrice>> sortByTotalGain(@RequestBody List<Stock> stocks) {
        return ResponseEntity.ok(orchestrator.orchestrate(stocks, "/sort-by-total-gain"));
    }
}
