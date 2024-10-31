package sky.pro.bankstar.configuration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() { // по умолчанию, настройки кэширования одинаковы для всех методов
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES) // Этот метод устанавливает время истечения кеша. Кеш будет истекать через 10 минут после записи значения
                .maximumSize(1000)); // максимальный размер кэша, будет хранить не более 1000 элементов
        return cacheManager;
    }
}