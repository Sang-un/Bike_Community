package bike.community.controller.image;

import bike.community.model.entity.board.image.ImageFile;
import bike.community.model.entity.board.image.ImageFiles;
import bike.community.model.network.Header;
import bike.community.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;

    @Value("${file.dir}")
    private String fileDir;


    /* -------------이미지--------------- */
    //이미지 저장. url 만들기
    @PostMapping("/api/image")
    public Header<ImageFile> saveImage(MultipartFile imageFile) throws IOException {
        return imageService.saveImage(imageFile);
    }

    @PostMapping("/api/images")
    public Header<ImageFiles> saveImages(List<MultipartFile> imageFiles) throws IOException {
        return imageService.saveImages(imageFiles);
    }


    @GetMapping("/api/image/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        // sdh1df14-12ee-2353-1593-sd34dfg434.png (filename) - > fileStore.getFullPath(filename)하면
        // file"/Users/leechanyoung/Downloads/image/communityImageFiles/sdh1df14-12ee-2353-1593-sd34dfg434.png 로 바뀐다.
//        fileStore.getFullPath(filename);
        return new UrlResource("file:"+fileDir+filename);
    }
    /* --------------------------------- */
}
