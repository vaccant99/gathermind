package woongjin.gatherMind.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Target: 이 애노테이션은 파라미터에만 사용할 수 있습니다.
//@Retention: 런타임까지 애노테이션 정보를 유지합니다.
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentMemberId {

}
