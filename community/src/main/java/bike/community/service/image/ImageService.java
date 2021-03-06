package bike.community.service.image;

import bike.community.component.FileStore;
import bike.community.model.entity.board.image.AttachedFile;
import bike.community.model.entity.board.image.ImageFile;
import bike.community.model.entity.board.image.ImageFiles;
import bike.community.model.network.Header;
import bike.community.repository.board.AttachedFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly=true)
@Service
public class ImageService {

    private final AttachedFileRepository attachedFileRepository;
    private final FileStore fileStore;

    @Transactional
    public Header<ImageFiles> saveImages(List<MultipartFile> imageFiles) throws IOException {
        List<AttachedFile> savedAttachedFiles = attachedFileRepository.saveAll(fileStore.storeFiles(imageFiles));
        return Header.OK(new ImageFiles(savedAttachedFiles));
    }

    public Header<ImageFile> saveImage(MultipartFile imageFile) throws IOException {
        AttachedFile file = attachedFileRepository.save(fileStore.storeFile(imageFile));
        return Header.OK(new ImageFile(file));

    }
}
