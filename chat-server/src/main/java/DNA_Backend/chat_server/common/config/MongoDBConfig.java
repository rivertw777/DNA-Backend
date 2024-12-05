package DNA_Backend.chat_server.common.config;

import DNA_Backend.chat_server.domain.chat.dto.ChatMessage;
import jakarta.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

@Configuration
@RequiredArgsConstructor
public class MongoDBConfig {

    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void init() {
        mongoTemplate.indexOps(ChatMessage.class).ensureIndex(
                new Index().on("createdAt", Sort.Direction.ASC).expire(1, TimeUnit.DAYS)
        );
    }

}
