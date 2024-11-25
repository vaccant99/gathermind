package woongjin.gatherMind.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class RequestResponseLoggingAspect {

    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void logRequest(JoinPoint joinPoint) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        log.info("Incoming request : {} {}", request.getMethod(), request.getRequestURI());
        log.info("Handler method : {}" , joinPoint.getSignature().toShortString());
    }

    @AfterReturning(value = "within(@org.springframework.web.bind.annotation.RestController *)", returning = "result")
    public void logResponse(JoinPoint joinPoint, Object result) {
        log.info("Handler method : {} comleted", joinPoint.getSignature().toShortString());
        log.info("Response : {}", result);
    }
}
