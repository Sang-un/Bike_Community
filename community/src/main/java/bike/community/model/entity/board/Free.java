package bike.community.model.entity.board;

import bike.community.model.entity.user.User;
import lombok.AllArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
//@DiscriminatorValue("bike")
public class Free extends Board{

    public static Free create(String title,  String content, User user) {
        Free free = new Free();
        free.title = title;
        free.content = content;
        free.user = user;
        user.addBoard(free);
        return free;
    }
}
