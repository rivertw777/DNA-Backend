package DNA_Backend.chat_server.global.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void increase(String key) {
        redisTemplate.opsForValue().increment(key);
    }

    public void decrease(String key) {
        redisTemplate.opsForValue().decrement(key);
    }

}
