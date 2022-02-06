package bike.community.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BoardType {

    // 바이크, 정비, 사진, 자유
    BIKE(0, "BIKE"),
    FIX(1, "FIX"),
    PICTURE(2, "PICTURE"),
    FREE(3, "FREE");

    private Integer id;
    private String title;

}
