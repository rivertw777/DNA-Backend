package DNA_Backend.api_server.domain.user.service;

import DNA_Backend.api_server.domain.user.model.entity.User;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserCacheService {

    private final String USER_CACHE_KEY_PREFIX = "UserCache:";

    @Qualifier("userRedisTemplate")
    private final RedisTemplate<String, User> userRedisTemplate;

    public void setUserCache(String username, User user) {
        String key = USER_CACHE_KEY_PREFIX + username;
        userRedisTemplate.opsForValue().set(key, user, Duration.ofHours(1));
        log.info("User {} Cache Saved", username);
    }

    public User getUserCache(String username) {
        String key = USER_CACHE_KEY_PREFIX + username;
        User user = userRedisTemplate.opsForValue().get(key);
        log.info("User {} Cache find", username);
        return user;
    }

    public void deleteUserCache(String username) {
        String key = USER_CACHE_KEY_PREFIX + username;
        userRedisTemplate.delete(key);
        log.info("User {} Cache Deleted", username);
    }

}