package TourData.backend.global.redis;

import TourData.backend.domain.chat.dto.ChatMessage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final Map<String, ChannelTopic> topics = new ConcurrentHashMap<>();

    public void publishMessage(ChatMessage chatMessage) {
        String topicName = "chatroom." + chatMessage.getRoomId();
        ChannelTopic topic = topics.computeIfAbsent(topicName, ChannelTopic::new);
        redisTemplate.convertAndSend(topic.getTopic(), chatMessage);
    }

}
