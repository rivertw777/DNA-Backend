package DNA_Backend.chat_server.chat.service;

import DNA_Backend.chat_server.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantCountService {

    private final RedisService redisService;

    public void initCount(Long roomId) {
        String key = getKey(String.valueOf(roomId));
        if (!redisService.exists(key)) {
            redisService.set(key, String.valueOf(0));
        }
    }

    public int getParticipantCount(String roomId) {
        String key = getKey(roomId);
        return Integer.valueOf(redisService.get(key));
    }

    public int increaseParticipantCount(String roomId) {
        String key = getKey(roomId);
        redisService.increase(key);
        return Integer.valueOf(redisService.get(key));
    }

    public int decreaseParticipantCount(String roomId) {
        String key = getKey(roomId);
        redisService.decrease(key);
        return Integer.valueOf(redisService.get(key));
    }

    private String getKey(String roomId) {
        return "room" + roomId + ":participantCount";
    }

}
