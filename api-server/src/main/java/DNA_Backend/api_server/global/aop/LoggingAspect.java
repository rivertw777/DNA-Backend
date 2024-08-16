package DNA_Backend.api_server.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    // 예외 발생 시 로깅
    @AfterThrowing(pointcut = "( execution(* DNA_Backend.api_server.domain.user.service.UserService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.location.service.LocationService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.location.service.LocationLikeService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.facility.service.FacilityService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.facility.service.FacilityBookmarkService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.workationSchedule.service.WorkationScheduleService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.workationSchedule.service.ScheduleNotificationService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.review.service.ReviewService..*(..)) || "
            + "execution(* DNA_Backend.api_server.global.security.auth.CustomUserDetailsService..*(..)))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String layer = getLayerName(className);

        log.error("[{}] [Exception] {}.{}(): {}", layer, className, methodName, ex.getMessage());
    }

    // 메서드 실행 이전 로깅
    @Before("( execution(* DNA_Backend.api_server.domain.user.service.UserService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.location.service.LocationService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.location.service.LocationLikeService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.facility.service.FacilityService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.facility.service.FacilityBookmarkService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.workationSchedule.service.WorkationScheduleService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.workationSchedule.service.ScheduleNotificationService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.review.service.ReviewService..*(..)) || "
            + "execution(* DNA_Backend.api_server.global.security.auth.CustomUserDetailsService..*(..)))")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String layer = getLayerName(className);

        log.info("[{}] [Executing] {}.{}()", layer, className, methodName);
    }

    // 메서드 실행 이후 로깅
    @Around("( execution(* DNA_Backend.api_server.domain.user.service.UserService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.location.service.LocationService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.location.service.LocationLikeService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.facility.service.FacilityService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.facility.service.FacilityBookmarkService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.workationSchedule.service.WorkationScheduleService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.workationSchedule.service.ScheduleNotificationService..*(..)) || "
            + "execution(* DNA_Backend.api_server.domain.review.service.ReviewService..*(..)) || "
            + "execution(* DNA_Backend.api_server.global.security.auth.CustomUserDetailsService..*(..)))")
    public Object logAfter(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String layer = getLayerName(className);

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        log.info("[{}] [Completed] {}.{}() - Execution Time: {}ms", layer, className, methodName, executionTime);
        return result;
    }

    // 클래스 이름으로부터 계층(layer) 이름 추출
    private String getLayerName(String className) {
        if (className.toLowerCase().contains("service")) {
            return "Service";
        }
        return "Unknown";
    }

}
