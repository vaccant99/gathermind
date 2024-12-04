package woongjin.gatherMind.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

@Slf4j
@Aspect
@Component
public class RequestResponseLoggingAspect {

    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void logRequest(JoinPoint joinPoint) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        // 요청 메서드와 URI 로깅
        log.info("Incoming request: {} {}", request.getMethod(), request.getRequestURI());
        log.info("Handler method: {}", joinPoint.getSignature().toShortString());

//        // 요청 헤더 로깅 (민감한 데이터 제외)
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            if (!headerName.equalsIgnoreCase("Authorization")) {
//                log.info("Request header: {} = {}", headerName, request.getHeader(headerName));
//            } else {
//                log.info("Request header: {} = [PROTECTED]", headerName);
//            }
//        }

        // 요청 파라미터 로깅
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (!paramName.toLowerCase().contains("password") && !paramName.toLowerCase().contains("token")) {
                log.info("Request parameter: {} = {}", paramName, request.getParameter(paramName));
            } else {
                log.info("Request parameter: {} = [PROTECTED]");
            }
        }
    }

    @AfterReturning(value = "within(@org.springframework.web.bind.annotation.RestController *)", returning = "result")
    public void logResponse(JoinPoint joinPoint, Object result) {
        log.info("Handler method: {} completed", joinPoint.getSignature().toShortString());

        // 응답 데이터 크기 로깅
        if (result instanceof String) {
            log.info("Response size: {} bytes", ((String) result).getBytes().length);
        } else if (result instanceof byte[]) {
            log.info("Response size: {} bytes", ((byte[]) result).length);
        } else {
            log.info("Response: {}", result); // 객체로 반환되는 경우
        }
    }

    @AfterThrowing(value = "within(@org.springframework.web.bind.annotation.RestController *)", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        log.error("Exception in method: {} with message: {}", joinPoint.getSignature().toShortString(), exception.getMessage(), exception);
    }
}
