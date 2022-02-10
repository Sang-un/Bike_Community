package bike.community.model.entity.board;

import bike.community.model.entity.user.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Entity
//@DiscriminatorValue("bike")
public class Bike extends Board{

    private String bikeName;// for test

    public static Bike create(String bikeName, String title,  String content, User user) {
        Bike bike = new Bike();
        bike.bikeName = bikeName;
        bike.title = title;
        bike.content = content;
        bike.user = user;
        bike.writer = user.getNickname();
        return bike;
    }

    //    @Builder
//    public Bike(Long id, String title, String writer, String content, BoardType type, Long views, User user, Long likes){
//        super();
//    }
}
