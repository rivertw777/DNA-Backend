package TourData.backend.global.redis.config;

import static TourData.backend.global.redis.config.CasheName.LOCATION_FACILITIES_COUNT;
import static TourData.backend.global.redis.config.CasheName.LOCATION_WEATHER;
import static TourData.backend.global.redis.config.CasheName.LOCATION;
import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class CacheConfig {

    @Bean(name = "redisCacheManager")
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConf())
                .withInitialCacheConfigurations(confMap())
                .build();
    }

    private RedisCacheConfiguration defaultConf() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(Duration.ofDays(1));
    }

    private Map<String, RedisCacheConfiguration> confMap() {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put(LOCATION.getValue(), defaultConf());
        cacheConfigurations.put(LOCATION_WEATHER.getValue(), defaultConf().entryTtl(Duration.ofHours(1)));
        cacheConfigurations.put(LOCATION_FACILITIES_COUNT.getValue(), defaultConf());
        return cacheConfigurations;
    }

}