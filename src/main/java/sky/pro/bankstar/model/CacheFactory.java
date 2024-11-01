package sky.pro.bankstar.model;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public class CacheFactory {
    public static <K, V> Cache<K, V> createCache(long expireAfterWrite, TimeUnit timeUnit, long maximumSize) {
        return Caffeine.newBuilder()
                .expireAfterWrite(expireAfterWrite, timeUnit)
                .maximumSize(maximumSize)
                .build();
    }
}
