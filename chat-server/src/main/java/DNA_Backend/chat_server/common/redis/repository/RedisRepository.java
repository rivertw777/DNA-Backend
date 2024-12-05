package DNA_Backend.chat_server.common.redis.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Long increase(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public Long decrease(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

}
