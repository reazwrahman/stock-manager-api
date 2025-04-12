package api.stock.manager.chain_of_responsibility;

import api.stock.manager.stock.RequestModel;

public interface RequestValidator {
    final String NO_ERROR_MESSAGE = "NO ERROR FOUND";

    ErrorResponse validate(RequestModel request);
}
