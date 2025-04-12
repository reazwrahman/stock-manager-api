package api.stock.manager.chain_of_responsibility;

import api.stock.manager.stock.RequestModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RequestSizeValidator implements RequestValidator {

    @Value("${max.request.size}")
    Integer m_maxSize;

    @Override
    public ErrorResponse validate(RequestModel request) {
        if (request.getStocks().size() > m_maxSize) {
            return new ErrorResponse(true, "The API currently supports up to " +
                    m_maxSize + " stocks per request");
        } else {
            return new ErrorResponse(false, NO_ERROR_MESSAGE);
        }
    }
}
