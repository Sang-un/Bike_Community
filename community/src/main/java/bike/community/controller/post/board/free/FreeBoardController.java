package bike.community.controller.post.board.free;

import bike.community.model.network.Header;
import bike.community.model.network.request.post.board.free.FreeBoardRequest;
import bike.community.model.network.response.post.board.free.FreeBoardPageResponse;
import bike.community.model.network.response.post.board.free.FreeBoardResponse;
import bike.community.service.board.free_board.FreeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FreeBoardController {

    private final FreeBoardService freeBoardService;

    @PostMapping("/api/board/free")
    public Header<FreeBoardResponse> create(@RequestBody FreeBoardRequest freeBoardRequest){
        return freeBoardService.create(freeBoardRequest);
    }

    @GetMapping("/api/board/free")
    public Header<Page<FreeBoardPageResponse>> searchPaging(Pageable pageable){
        return freeBoardService.searchPaging(pageable);
    }
}
