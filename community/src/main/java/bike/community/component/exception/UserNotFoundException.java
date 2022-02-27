package bike.community.component.exception;

import org.springframework.security.core.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {
    public UserNotFoundException(String userEmail){
        super(userEmail + " 의 이메일을 가진 사용자를 찾을 수 없습니다.");
    }
}
