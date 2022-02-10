package bike.community.model.entity.board;

import bike.community.model.entity.user.User;
import lombok.AllArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
//@DiscriminatorValue("bike")
public class Free extends Board{
}
