package api.stock.manager.chain_of_responsibility;

import api.stock.manager.stock.RequestModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccessKeyValidator implements RequestValidator {

    @Value("${access.key}")
    String m_accessKey;

    @Override
    public ErrorResponse validate(RequestModel request) {
        if (request.getAccessKey() != null && request.getAccessKey().equals(m_accessKey)) {
            return new ErrorResponse(false, NO_ERROR_MESSAGE);
        } else {
            return new ErrorResponse(true, "Invalid Access Key");
        }
    }
}
