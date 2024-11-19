package woongjin.gatherMind.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woongjin.gatherMind.config.JwtTokenProvider;

@Component
@RequiredArgsConstructor
public class MemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 매개변수가 @CurrentMemberId 애노테이션을 가지고 있고, String 타입이면 처리
        return parameter.hasParameterAnnotation(CurrentMemberId.class) &&
                parameter.getParameterType().equals(String.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        // HTTP 요청에서 JWT를 추출하고 memberId 반환
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return jwtTokenProvider.extractMemberIdFromRequest(request);
    }
}
