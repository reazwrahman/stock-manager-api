package api.stock.manager.strategy;

import api.stock.manager.adapter.PriceHandler;
import api.stock.manager.strategy.cache.CacheInterface;
import lombok.Data;
import org.springframework.cache.Cache;

@Data
public class CacheStrategyParameters {
    PriceHandler adapter;
    CacheInterface cache;
    Integer ttl;

    public CacheStrategyParameters(PriceHandler adapter, CacheInterface cache, Integer ttl){
        this.adapter = adapter;
        this.cache = cache;
        this.ttl = ttl;
    }
}
