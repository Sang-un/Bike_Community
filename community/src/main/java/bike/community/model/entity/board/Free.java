package bike.community.model.entity.board;

import bike.community.component.FileStore;
import bike.community.model.entity.user.User;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import java.io.IOException;
import java.util.List;

@Entity
//@DiscriminatorValue("bike")
public class Free extends Board{

    public static Free create(String title, String content, User user) throws IOException {
        Free free = new Free();
        free.title = title;
        free.content = content;
        free.user = user;
        user.addBoard(free);
        return free;
    }
}
