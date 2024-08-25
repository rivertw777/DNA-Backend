package DNA_Backend.api_server.global.redis.config;

import static DNA_Backend.api_server.global.redis.config.CasheName.ALL_LOCATIONS;
import static DNA_Backend.api_server.global.redis.config.CasheName.ALL_LOCATION_TOTAL_FACILITY_COUNTS;
import static DNA_Backend.api_server.global.redis.config.CasheName.ALL_LOCATION_WEATHER;
import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@Configuration
public class RedisCacheConfig {

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
        cacheConfigurations.put(ALL_LOCATIONS.getValue(), defaultConf());
        cacheConfigurations.put(ALL_LOCATION_WEATHER.getValue(), defaultConf().entryTtl(Duration.ofHours(1)));
        cacheConfigurations.put(ALL_LOCATION_TOTAL_FACILITY_COUNTS.getValue(), defaultConf());
        return cacheConfigurations;
    }

}