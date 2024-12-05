package DNA_Backend.chat_server.common.redis.messaging;

import DNA_Backend.chat_server.domain.chat.dto.ChatMessage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final Map<String, ChannelTopic> topics = new ConcurrentHashMap<>();

    public void publishMessage(ChatMessage chatMessage) {
        String topicName = "chatroom:" + chatMessage.getRoomId();
        ChannelTopic topic = topics.computeIfAbsent(topicName, ChannelTopic::new);
        try {
            redisTemplate.convertAndSend(topic.getTopic(), chatMessage);
            log.info("Message published to topic {}: {}", topic.getTopic(), chatMessage);
        } catch (Exception e) {
            log.error("Failed to publish message to topic {}: {}", topic.getTopic(), e.getMessage(), e);
        }
    }

}
