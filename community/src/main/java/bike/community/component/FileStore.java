package bike.community.component;

import bike.community.model.entity.board.image.AttachedFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public List<AttachedFile> storeFiles(List<MultipartFile> imageFiles) throws IOException {
        List<AttachedFile> storeFileResult = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            if(!imageFile.isEmpty()){
                AttachedFile attachedFile = storeFile(imageFile);
                storeFileResult.add(attachedFile);
            }
        }
        return storeFileResult;
    }

    public AttachedFile storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()) return null;
        String originalFilename = multipartFile.getOriginalFilename();
        assert originalFilename != null;
        String storeFilename = UUID.randomUUID() +"."+ extractExt(originalFilename);

        multipartFile.transferTo(new File(getFullPath(storeFilename)));
        return AttachedFile.createAttachedFile(originalFilename, storeFilename);
    }

    private String extractExt(String originalFilename) {
        int extIndex = originalFilename.lastIndexOf('.');
        return originalFilename.substring(extIndex + 1);
    }

    public String getFullPath(String filename){
        return fileDir + filename;
    }
}
