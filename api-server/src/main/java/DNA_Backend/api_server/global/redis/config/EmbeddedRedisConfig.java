package DNA_Backend.api_server.global.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Profile("de")
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.redis.maxheap}")
    private String redisMaxHeap;

    private RedisServer redisServer;

    @Bean
    public RedisServer redisServer() throws Exception {
        redisServer = RedisServer.builder()
                .port(redisPort)
                .setting(redisMaxHeap)
                .build();
        redisServer.start();
        return redisServer;
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}