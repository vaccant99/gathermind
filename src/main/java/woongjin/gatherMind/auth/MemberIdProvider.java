package woongjin.gatherMind.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import woongjin.gatherMind.config.JwtTokenProvider;



//RequestScope
//각 HTTP 요청마다 새로운 인스턴스가 생성되고, 해당 요청이 끝나면 인스턴스가 사라집니다.
//요청 단위 데이터 유지: 요청마다 새로운 인스턴스를 생성하므로, 요청마다 다른 데이터를 저장할 수 있습니다.
//인증 정보 처리: 예를 들어, MemberIdProvider와 같이 인증 토큰에서 추출한 사용자 ID를 유지할 때 유용합니다. 각 요청마다 해당 ID를 추출하여 컨트롤러나 서비스에서 사용할 수 있습니다.
//비즈니스 로직의 중복 제거: 여러 메서드에서 동일한 데이터를 사용해야 할 때, @RequestScope를 사용하면 데이터 추출 로직을 한 번만 수행하고, 나머지 코드에서 쉽게 접근할 수 있습니다.

//@Transactional과 @RequestScope가 함께 사용되면 트랜잭션 경계가 요청 컨텍스트와 맞지 않기 때문에 빈 생성을 실패할 수 있습니다.

@Component
@SessionScope
public class MemberIdProvider {

    private final String memberId;

    public MemberIdProvider(JwtTokenProvider jwtTokenProvider, HttpServletRequest request) {
        this.memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
    }

    public String getMemberId() {
        return memberId;
    }
}
