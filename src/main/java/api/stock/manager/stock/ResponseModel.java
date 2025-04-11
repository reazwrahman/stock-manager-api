package api.stock.manager.stock;

import lombok.Data;

import java.util.List;

@Data
public class ResponseModel {
    List<StockWithPrice> stocks;
    public ResponseModel(List<StockWithPrice> stocks){
        this.stocks = stocks;
    }
}
