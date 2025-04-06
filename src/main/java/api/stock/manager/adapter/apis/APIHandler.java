package api.stock.manager.adapter.apis;

import api.stock.manager.adapter.PriceHandler;

public interface APIHandler extends PriceHandler {
    void prepareRequestBody();
    void sendRequest();
    void parseResponse();
}
