package api.stock.manager;

import api.stock.manager.fascade.TaskOrchestrator;
import api.stock.manager.stock.RequestModel;
import api.stock.manager.stock.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RequestController {

    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    @Autowired
    TaskOrchestrator orchestrator;

    @PostMapping("/sort-by-return-rate")
    public ResponseEntity<ResponseModel> sortByReturnRate(@RequestBody RequestModel request) {

        logger.info("RequestController /sort-by-return-rate Request: " + request);
        return ResponseEntity.ok(orchestrator.orchestrate(request.getStocks(), "/sort-by-return-rate"));
    }

    @PostMapping("/sort-by-total-gain")
    public ResponseEntity<ResponseModel> sortByTotalGain(@RequestBody RequestModel request) {
        logger.info("RequestController /sort-by-return-rate Request: " + request);
        return ResponseEntity.ok(orchestrator.orchestrate(request.getStocks(), "/sort-by-total-gain"));
    }
}
