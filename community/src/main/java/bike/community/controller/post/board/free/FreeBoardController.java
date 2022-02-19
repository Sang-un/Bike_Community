package bike.community.controller.post.board.free;

import bike.community.component.FileStore;
import bike.community.model.entity.board.ImageFiles;
import bike.community.model.network.Header;
import bike.community.model.network.request.post.board.free.FreeBoardRequest;
import bike.community.model.network.response.post.board.free.FreeBoardPageResponse;
import bike.community.model.network.response.post.board.free.FreeBoardResponse;
import bike.community.model.network.response.post.board.free.search_condition.FreeBoardSearchCond;
import bike.community.service.board.free_board.FreeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class FreeBoardController {

    private final FreeBoardService freeBoardService;

    //게시물 작성하기
    @PostMapping("/api/board/free")
    public Header<FreeBoardResponse> create(@RequestBody FreeBoardRequest freeBoardRequest, HttpServletRequest request) throws IOException {
        return freeBoardService.create(freeBoardRequest, request);
    }

    //게시판 들어갔을 때 전체 게시물들 보기
    @GetMapping("/api/board/free")
    public Header<Page<FreeBoardPageResponse>> searchPaging(Pageable pageable){
        return freeBoardService.searchPaging(pageable);
    }

    //게시물 하나 조회
    @GetMapping("/api/board/free/{id}")
    public Header<FreeBoardResponse> findOne(@PathVariable Long id){
        return freeBoardService.findOne(id);
    }

    //조건에 따른 게시물 조회
    @GetMapping("/api/board/free/search")
    public Header<Page<FreeBoardPageResponse>> searchPaging(FreeBoardSearchCond cond, Pageable pageable){
        return freeBoardService.searchByCond(cond, pageable);
    }

    // TODO 게시물 수정하기
    @PutMapping("/api/board/free/{id}")
    public Header<FreeBoardResponse> update(@PathVariable Long id){
        return freeBoardService.findOne(id);
    }
}
