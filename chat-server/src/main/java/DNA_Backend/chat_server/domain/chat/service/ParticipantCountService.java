package DNA_Backend.chat_server.domain.chat.service;

import DNA_Backend.chat_server.common.redis.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantCountService {

    private final RedisRepository redisRepository;

    public long getParticipantCount(String roomId) {
        String key = getKey(roomId);
        return Long.parseLong(redisRepository.get(key));
    }

    public long increaseParticipantCount(String roomId) {
        String key = getKey(roomId);
        return redisRepository.increase(key);
    }

    public long decreaseParticipantCount(String roomId) {
        String key = getKey(roomId);
        return redisRepository.decrease(key);
    }

    private String getKey(String roomId) {
        return "room" + roomId + ":participantCount";
    }

}
