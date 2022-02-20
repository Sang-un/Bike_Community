package bike.community.model.entity.board.image;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImageFiles {

    private final String url = "http://localhost:8080/api/image/";
    private List<String> imageUrls = new ArrayList<>();

    public ImageFiles(List<AttachedFile> attachedFiles) {
        for (AttachedFile attachedFile : attachedFiles)
            this.imageUrls.add(url+attachedFile.getStoreFilename());
    }
}
