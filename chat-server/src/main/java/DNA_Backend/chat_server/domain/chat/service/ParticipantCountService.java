package DNA_Backend.chat_server.domain.chat.service;

import DNA_Backend.chat_server.global.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantCountService {

    private final RedisService redisService;

    public long getParticipantCount(String roomId) {
        String key = getKey(roomId);
        return Long.valueOf(redisService.get(key));
    }

    public long increaseParticipantCount(String roomId) {
        String key = getKey(roomId);
        redisService.increase(key);
        return Long.valueOf(redisService.get(key));
    }

    public long decreaseParticipantCount(String roomId) {
        String key = getKey(roomId);
        redisService.decrease(key);
        return Long.valueOf(redisService.get(key));
    }

    private String getKey(String roomId) {
        return "room" + roomId + ":participantCount";
    }

}
