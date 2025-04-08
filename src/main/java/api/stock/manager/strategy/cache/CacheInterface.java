package api.stock.manager.strategy.cache;

import java.math.BigDecimal;
import java.util.Map;

public interface CacheInterface {
    void addData(CachableData data);

    void removeData(String ticker);

    CachableData getData(String ticker);

    boolean exists(String ticker);

    void addData(Map<String, BigDecimal> batch);
}
