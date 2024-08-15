package DNA_Backend.api_server.global.security.config.handler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "uri")
public class UriProperties {

    private String scheme;
    private String host;
    private int port;
    private String path;

}