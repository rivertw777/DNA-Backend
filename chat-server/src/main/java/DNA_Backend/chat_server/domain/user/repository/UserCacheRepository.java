package DNA_Backend.chat_server.domain.user.repository;

import DNA_Backend.chat_server.domain.user.model.Role;
import DNA_Backend.chat_server.domain.user.model.UserCache;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;


@Slf4j
@Repository
@RequiredArgsConstructor
public class UserCacheRepository {

    private final String USER_CACHE_KEY_PREFIX = "UserCache:";

    @Qualifier("userRedisTemplate")
    private final RedisTemplate<String, UserCache> userRedisTemplate;

    public UserCache getUserCache(String username) {
        String key = USER_CACHE_KEY_PREFIX + username;
        //UserCache user = userRedisTemplate.opsForValue().get(key);
        UserCache user = new UserCache(1L, "test", "test@naver.com", "123456", Collections.singletonList(Role.USER));
        log.info("User {} Cache find", username);
        return user;
    }

}
