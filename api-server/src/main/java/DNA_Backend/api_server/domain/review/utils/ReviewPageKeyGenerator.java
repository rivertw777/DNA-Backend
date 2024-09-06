package DNA_Backend.api_server.domain.review.utils;

import java.lang.reflect.Method;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

@Component("ReviewPageKeyGenerator")
public class ReviewPageKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        Object locationId = (params.length > 1) ? params[1] : "SimpleKey []";
        return locationId;
    }
}



