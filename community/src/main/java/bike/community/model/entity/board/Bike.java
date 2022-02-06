package bike.community.model.entity.board;

import bike.community.model.entity.user.User;
import bike.community.model.enumclass.BoardType;
import lombok.Builder;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@DiscriminatorValue("bike")
public class Bike extends Board{


//    @Builder
//    public Bike(Long id, String title, String writer, String content, BoardType type, Long views, User user, Long likes){
//        super();
//    }
}
