package DNA_Backend.api_server.domain.workationReview.utils;

import java.lang.reflect.Method;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

@Component("WorkationReviewPageKeyGenerator")
public class WorkationReviewPageKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return (params.length > 1) ? params[1] : "SimpleKey []";
    }
}




