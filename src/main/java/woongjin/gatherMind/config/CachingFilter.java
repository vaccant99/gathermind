//package woongjin.gatherMind.config;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.util.ContentCachingRequestWrapper;
//import org.springframework.web.util.ContentCachingResponseWrapper;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
//public class CachingFilter  extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        // 요청과 응답을 각각 캐싱 가능한 래퍼로 감쌉니다.
//        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
//        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
//
//        try {
//            filterChain.doFilter(wrappedRequest, wrappedResponse);
//        } finally {
//            // 캐싱된 요청 및 응답 내용을 로깅하거나 처리합니다.
//            logRequestBody(wrappedRequest);
//            wrappedResponse.copyBodyToResponse(); // 응답 데이터를 클라이언트로 전송
//        }
//    }
//
//    private void logRequestBody(ContentCachingRequestWrapper request) {
//        String body = new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);
//        logger.info("Request body: " + body);
//    }
//}