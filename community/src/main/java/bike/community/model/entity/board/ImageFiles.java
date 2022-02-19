package bike.community.model.entity.board;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImageFiles {

    private List<String> imageFiles = new ArrayList<>();

    public ImageFiles(List<AttachedFile> attachedFiles) {
        for (AttachedFile attachedFile : attachedFiles) this.imageFiles.add(attachedFile.getStoreFilename());
    }
}
