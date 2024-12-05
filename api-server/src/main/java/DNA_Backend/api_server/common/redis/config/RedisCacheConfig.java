package DNA_Backend.api_server.common.redis.config;

import static DNA_Backend.api_server.common.redis.config.CacheName.ALL_LOCATIONS;
import static DNA_Backend.api_server.common.redis.config.CacheName.ALL_LOCATION_TOTAL_FACILITY_COUNTS;
import static DNA_Backend.api_server.common.redis.config.CacheName.ALL_LOCATION_WEATHERS;
import static DNA_Backend.api_server.common.redis.config.CacheName.All_LOCATION_WORKATION_REVIEWS;
import static DNA_Backend.api_server.common.redis.config.CacheName.FACILITY_SEARCH_RESULTS;
import static DNA_Backend.api_server.common.redis.config.CacheName.LOCATION_DETAIL;
import static DNA_Backend.api_server.common.redis.config.CacheName.LOCATION_TOTAL_FACILITY_COUNT;
import static DNA_Backend.api_server.common.redis.config.CacheName.LOCATION_WEATHER;
import static DNA_Backend.api_server.common.redis.config.CacheName.LOCATION_WORKATION_REVIEWS;
import static DNA_Backend.api_server.common.redis.config.CacheName.WORKATION_OFFICE_SEARCH_RESULTS;
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
                .entryTtl(Duration.ofHours(1));
    }

    private Map<String, RedisCacheConfiguration> confMap() {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        cacheConfigurations.put(ALL_LOCATIONS.getValue(), defaultConf().entryTtl(Duration.ofDays(1)));
        cacheConfigurations.put(LOCATION_DETAIL.getValue(), defaultConf());

        cacheConfigurations.put(ALL_LOCATION_WEATHERS.getValue(), defaultConf());
        cacheConfigurations.put(LOCATION_WEATHER.getValue(), defaultConf());

        cacheConfigurations.put(FACILITY_SEARCH_RESULTS.getValue(), defaultConf().entryTtl(Duration.ofDays(1)));
        cacheConfigurations.put(WORKATION_OFFICE_SEARCH_RESULTS.getValue(), defaultConf().entryTtl(Duration.ofDays(1)));

        cacheConfigurations.put(ALL_LOCATION_TOTAL_FACILITY_COUNTS.getValue(), defaultConf().entryTtl(Duration.ofDays(1)));
        cacheConfigurations.put(LOCATION_TOTAL_FACILITY_COUNT.getValue(), defaultConf().entryTtl(Duration.ofDays(1)));

        cacheConfigurations.put(All_LOCATION_WORKATION_REVIEWS.getValue(), defaultConf());
        cacheConfigurations.put(LOCATION_WORKATION_REVIEWS.getValue(), defaultConf());

        return cacheConfigurations;
    }
}
