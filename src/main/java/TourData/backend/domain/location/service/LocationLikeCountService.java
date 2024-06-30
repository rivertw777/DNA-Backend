package TourData.backend.domain.location.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationLikeCountService {

    private final StringRedisTemplate redisTemplate;

    public void initCount(Long locationId) {
        redisTemplate.opsForValue().set("locationLikeCount:" + locationId, String.valueOf(0));
    }

    public void increaseCount(Long locationId) {
        redisTemplate.opsForValue().increment("locationLikeCount:" + locationId);
    }

    public void decreaseCount(Long locationId) {
        redisTemplate.opsForValue().decrement("locationLikeCount:" + locationId);
    }

    public int getCount(Long locationId) {
        String count = redisTemplate.opsForValue().get("locationLikeCount:" + locationId);
        return Integer.parseInt(count);
    }

}