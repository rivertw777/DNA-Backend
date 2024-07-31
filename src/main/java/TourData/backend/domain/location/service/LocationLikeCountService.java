package TourData.backend.domain.location.service;

import TourData.backend.global.redis.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class LocationLikeCountService extends RedisService {

    private static final String LOCATION_LIKE_COUNT_KEY = "locationLikeCount:";

    public LocationLikeCountService(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }

    public void initCount(Long locationId) {
        String key = LOCATION_LIKE_COUNT_KEY + locationId;
        if (!exists(key)) {
            save(key, String.valueOf(0));
        }
    }

    public void increaseCount(Long locationId) {
        increase(LOCATION_LIKE_COUNT_KEY + locationId);
    }

    public void decreaseCount(Long locationId) {
        decrease(LOCATION_LIKE_COUNT_KEY + locationId);
    }

    public long getCount(Long locationId) {
        String count = (String) get(LOCATION_LIKE_COUNT_KEY + locationId);
        return count != null ? Long.parseLong(count) : 0L;
    }

}

