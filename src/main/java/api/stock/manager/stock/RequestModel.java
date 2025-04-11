package api.stock.manager.stock;

import lombok.Data;

import java.util.List;

@Data
public class RequestModel {
    String accessKey;
    List<Stock> stocks;
}
