package bike.community.model.entity.board.image;

public class ImageFile {

    private final String url = "http://localhost:8080/api/image/";
    private String imageUrl;

    public ImageFile(AttachedFile file) {
        this.imageUrl = url + file.getStoreFilename();
    }
}
