package api.stock.manager.chain_of_responsibility;

import api.stock.manager.stock.RequestModel;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service("validatorEntryPoint")
public class ValidatorEntryPoint implements RequestValidator {

    LinkedList<RequestValidator> validatorChain = new LinkedList<>();

    @Autowired AccessKeyValidator accessKeyValidator;
    @Autowired RequestSizeValidator sizeValidator;
    @Autowired StockBodyValidator bodyValidator;

    @PostConstruct
    public void init() {
        validatorChain.add(accessKeyValidator);
        validatorChain.add(sizeValidator);
        validatorChain.add(bodyValidator);
    }

    @Override
    public ErrorResponse validate(RequestModel request) {
        for (RequestValidator validator : validatorChain) {
            ErrorResponse response = validator.validate(request);
            if (response.errorFound) {
                return response;
            }
        }
        return new ErrorResponse(false, NO_ERROR_MESSAGE);
    }
}
