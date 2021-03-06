package bike.community.controller.image;

import bike.community.model.network.Header;
import bike.community.service.image.AwsS3Service;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    /**
     * Amazon S3에 이미지 업로드
     * @return 성공 시 200 Success와 함께 업로드 된 파일의 파일명 리스트 반환
     */
    @ApiOperation(value = "Amazon S3에 이미지 업로드", notes = "Amazon S3에 이미지 업로드 ")
    @PostMapping("/api/s3/images")
    public Header<List<String>> uploadImages(@ApiParam(value="img 파일들(여러 파일 업로드 가능)", required = true) @RequestPart List<MultipartFile> imageFiles) {
        return Header.OK(awsS3Service.uploadImages(imageFiles));
    }

    @ApiOperation(value = "Amazon S3에 이미지 업로드", notes = "Amazon S3에 이미지 업로드 ")
    @PostMapping("/api/s3/image")
    public Header<String> uploadImage(@ApiParam(value="img 파일들(여러 파일 업로드 가능)", required = true) @RequestPart MultipartFile imageFile) {
        return Header.OK(awsS3Service.uploadImage(imageFile));
    }

    /**
     * Amazon S3에 이미지 업로드 된 파일을 삭제
     * @return 성공 시 200 Success
     */
    @ApiOperation(value = "Amazon S3에 업로드 된 파일을 삭제", notes = "Amazon S3에 업로드된 이미지 삭제")
    @DeleteMapping("/api/s3/image")
    public Header<Void> deleteImage(@ApiParam(value="img 파일 하나 삭제", required = true) @RequestParam String fileName) {
        awsS3Service.deleteImage(fileName);
//        return ApiResponse.success(null);
        return Header.OK(null);
    }
}
