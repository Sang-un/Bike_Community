package bike.community.service;

import bike.community.model.network.Header;
import bike.community.model.entity.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CrudServiceInterface<Request, Response> {

    Header<Response> create(User user, Request request, MultipartFile[] files);

    Header<Response> update(User user, Request request, MultipartFile[] files, List<Long> delFileIdList);

    Header delete(User user, Long id);
}
