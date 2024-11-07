package woongjin.gatherMind.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

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
        log.info("Starting method: {}", methodName);

        long startTime = System.currentTimeMillis();

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            log.error("Exception in method : {} with message : {}", methodName, e.getMessage() );
            throw e;
        }

        long endTime = System.currentTimeMillis();
        log.info("Completed method : {} in {} ms", methodName, (endTime - startTime));

        return result;
    }

}
