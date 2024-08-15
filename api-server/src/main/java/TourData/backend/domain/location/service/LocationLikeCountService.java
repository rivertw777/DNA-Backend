package TourData.backend.domain.location.service;

import TourData.backend.global.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationLikeCountService {

    private static final String LOCATION_LIKE_COUNT_KEY = "locationLikeCount:";

    private final RedisService redisService;

    public void initCount(Long locationId) {
        String key = LOCATION_LIKE_COUNT_KEY + locationId;
        if (!redisService.exists(key)) {
            redisService.set(key, String.valueOf(0L));
        }
    }

    public void increaseCount(Long locationId) {
        redisService.increase(LOCATION_LIKE_COUNT_KEY + locationId);
    }

    public void decreaseCount(Long locationId) {
        redisService.decrease(LOCATION_LIKE_COUNT_KEY + locationId);
    }

    public long getCount(Long locationId) {
        String count = redisService.get(LOCATION_LIKE_COUNT_KEY + locationId);
        return count != null ? Long.valueOf(count) : 0L;
    }

}


