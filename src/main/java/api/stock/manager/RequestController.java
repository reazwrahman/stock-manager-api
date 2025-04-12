package api.stock.manager;

import api.stock.manager.chain_of_responsibility.ErrorResponse;
import api.stock.manager.chain_of_responsibility.ValidatorEntryPoint;
import api.stock.manager.facade.TaskOrchestrator;
import api.stock.manager.stock.RequestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RequestController {

    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    @Autowired
    TaskOrchestrator orchestrator;

    @Autowired
    ValidatorEntryPoint requestValidator;

    @PostMapping("/sort-by-return-rate")
    public ResponseEntity<?> sortByReturnRate(@RequestBody RequestModel request) {
        return handleRequest(request, "/sort-by-return-rate");
    }

    @PostMapping("/sort-by-total-gain")
    public ResponseEntity<?> sortByTotalGain(@RequestBody RequestModel request) {
        return handleRequest(request, "/sort-by-total-gain");
    }

    private ResponseEntity<?> handleRequest(@RequestBody RequestModel request, String path) {
        logger.info("RequestController " + path + " Request: " + request);
        ErrorResponse validation = requestValidator.validate(request);
        if (validation.errorFound) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validation.errorMessage);
        }

        // example of facade: Having the orchestrator manage all the complexity of generating the response
        return ResponseEntity.ok(orchestrator.orchestrate(request.getStocks(), path));

    }
}

