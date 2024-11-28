package woongjin.gatherMind.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    // 포인트컷 : 서비스 계층의 모든 메서드를 대상으로
    @Pointcut("execution(* woongjin.gatherMind.service..*(..))")
    public void serviceMethods() {}

    // Around Advice : 메서드  실행 전후에 로그 추가
    @Around("serviceMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Object[] maskedArgs = Arrays.stream(args)
                .map(this::maskSensitiveFields)
                .toArray();
        log.info("Starting method: {} with args: {}", methodName, maskedArgs);

        Instant start = Instant.now();
        Object result;

        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            log.error("Exception in method : {} with message : {}", methodName, e.getMessage() );
            throw e;
        }

        Instant end = Instant.now();
        log.info("Completed method: {} in {} ms", methodName, Duration.between(start, end).toMillis());

        return result;
    }

    private Object maskSensitiveFields(Object arg) {
        if (arg == null) return null;

        try {
            Class<?> clazz = arg.getClass();

            // 시스템 클래스(java.lang.String 등) 무시
            if (clazz.getPackageName().startsWith("java.")) {
                return arg;
            }


            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getName().toLowerCase().contains("password")) {
                    field.set(arg, "********");
                }
            }
        } catch (IllegalAccessException e) {
            log.error("Error masking sensitive data", e);
        }
        return arg;
    }

}
