package bike.community.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Getter
public class Address {
    private String address;
    private String detailAddress;
    private String zipcode;
}
