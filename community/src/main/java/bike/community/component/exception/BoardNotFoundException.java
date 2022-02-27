package bike.community.component.exception;

import org.springframework.security.core.AuthenticationException;

public class BoardNotFoundException extends AuthenticationException {
    public BoardNotFoundException(Long boardId){
        super(boardId + "에 해당하는 게시글을 찾을 수 없습니다.");
    }
}
