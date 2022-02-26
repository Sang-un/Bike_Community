package bike.community.model.entity.comment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Comment {

    @Id @GeneratedValue
    private Long id;

    @Lob
    private String comment;

    
}
