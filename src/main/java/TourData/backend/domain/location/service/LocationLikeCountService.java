package TourData.backend.domain.location.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationLikeCountService {

    private static final String LOCATION_LIKE_COUNT_KEY = "locationLikeCount:";

    private final StringRedisTemplate redisTemplate;

    public void initCount(Long locationId) {
        redisTemplate.opsForValue().setIfAbsent(LOCATION_LIKE_COUNT_KEY + locationId, String.valueOf(0));
    }

    public void increaseCount(Long locationId) {
        redisTemplate.opsForValue().increment(LOCATION_LIKE_COUNT_KEY + locationId);
    }

    public void decreaseCount(Long locationId) {
        redisTemplate.opsForValue().decrement(LOCATION_LIKE_COUNT_KEY + locationId);
    }

    public long getCount(Long locationId) {
        String count = redisTemplate.opsForValue().get(LOCATION_LIKE_COUNT_KEY + locationId);
        return count != null ? Long.parseLong(count) : 0L;
    }

}
