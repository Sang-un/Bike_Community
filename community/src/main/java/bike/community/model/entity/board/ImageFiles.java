package bike.community.model.entity.board;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImageFiles {

    private final String url = "http://localhost:8080/api/image/";
    private List<String> imagePaths = new ArrayList<>();

    public ImageFiles(List<AttachedFile> attachedFiles) {
        for (AttachedFile attachedFile : attachedFiles)
            this.imagePaths.add(url+attachedFile.getStoreFilename());
    }
}
