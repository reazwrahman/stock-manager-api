package api.stock.manager;

import api.stock.manager.fascade.TaskOrchestrator;
import api.stock.manager.stock.RequestModel;
import api.stock.manager.stock.ResponseModel;
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
    public ResponseEntity<ResponseModel> sortByReturnRate(@RequestBody RequestModel request) {

        // example of facade pattern: orchestrator hides all the complexity
        return ResponseEntity.ok(orchestrator.orchestrate(request.getStocks(), "/sort-by-return-rate"));
    }

    @PostMapping("/sort-by-total-gain")
    public ResponseEntity<ResponseModel> sortByTotalGain(@RequestBody RequestModel request) {
        return ResponseEntity.ok(orchestrator.orchestrate(request.getStocks(), "/sort-by-total-gain"));
    }
}
