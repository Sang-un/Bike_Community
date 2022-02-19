package bike.community.service.board.free_board;

import bike.community.component.FileStore;
import bike.community.model.entity.board.AttachedFile;
import bike.community.model.entity.board.Free;
import bike.community.model.entity.board.ImageFiles;
import bike.community.model.entity.user.User;
import bike.community.model.network.Header;
import bike.community.model.network.request.post.board.free.FreeBoardRequest;
import bike.community.model.network.response.post.board.free.FreeBoardPageResponse;
import bike.community.model.network.response.post.board.free.FreeBoardResponse;
import bike.community.model.network.response.post.board.free.search_condition.FreeBoardSearchCond;
import bike.community.model.network.response.user.UserWriterResponse;
import bike.community.repository.board.AttachedFileRepository;
import bike.community.repository.board.free_board.FreeBoardRepository;
import bike.community.repository.user.UserRepository;
import bike.community.security.jwt.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FreeBoardService {

    private final FreeBoardRepository freeBoardRepository;
    private final UserRepository userRepository;
    private final TokenUtils tokenUtils;

//    private final AttachedFileRepository attachedFileRepository;
//    private final FileStore fileStore;

//    @Transactional
//    public Header<FreeBoardResponse> create(FreeBoardRequest freeBoardRequest) {
//        User user = userRepository.findUserByNickname(freeBoardRequest.getNickname());
//        Free freeBoard = Free.create(freeBoardRequest.getTitle(), freeBoardRequest.getContent(), user);
//        freeBoardRepository.save(freeBoard);
//        FreeBoardResponse freeBoardResponse = FreeBoardResponse.builder()
//                .id(freeBoard.getId())
//                .title(freeBoard.getTitle())
//                .content(freeBoard.getContent())
//                .user(
//                        UserWriterResponse.builder().email(user.getEmail()).nickname(user.getNickname()).build()
//                )
//                .build();
//        return Header.OK(freeBoardResponse);
//    }

    @Transactional
    public Header<FreeBoardResponse> create(FreeBoardRequest freeBoardRequest, HttpServletRequest request) throws IOException {
        User user = userRepository.findUserByNickname(tokenUtils.getNicknameFromJwt(request));

        Free freeBoard = Free.create(freeBoardRequest.getTitle(),
                freeBoardRequest.getContent(),
//                freeBoardRequest.getImageFiles(),
                user);
        freeBoardRepository.save(freeBoard);

        /// -- 저장된 filename 들을 가져온다. --
//        List<AttachedFile> attachedFiles = freeBoard.getAttachedFiles();
//        List<String> imageFiles = new ArrayList<>();
//        for (AttachedFile attachedFile : attachedFiles) imageFiles.add(attachedFile.getStoreFilename());
        // --------------------------------

        FreeBoardResponse freeBoardResponse
                = new FreeBoardResponse(
                            freeBoard.getId(),
                            freeBoard.getTitle(),
                            freeBoard.getContent(),
//                            imageFiles,
                            new UserWriterResponse(user.getNickname(), user.getEmail())
                        );

        return Header.OK(freeBoardResponse);
    }

    public Header<Page<FreeBoardPageResponse>> searchPaging(Pageable pageable) {
        return Header.OK(freeBoardRepository.searchPaging(pageable));
    }

    public Header<FreeBoardResponse> findOne(Long id) {
        if(freeBoardRepository.findById(id).isPresent()){
            Free freeBoard = freeBoardRepository.findById(id).get();

//            List<AttachedFile> attachedFiles = freeBoard.getAttachedFiles();
//            List<String> imageFiles = new ArrayList<>();
//            for (AttachedFile attachedFile : attachedFiles) imageFiles.add(attachedFile.getStoreFilename());
            // --------------------------------

            FreeBoardResponse freeBoardResponse
                    = new FreeBoardResponse(
                    freeBoard.getId(),
                    freeBoard.getTitle(),
                    freeBoard.getContent(),
//                    imageFiles,
                    new UserWriterResponse(freeBoard.getUser().getNickname(), freeBoard.getUser().getEmail())
            );

            return Header.OK(freeBoardResponse);
        }
        return Header.ERROR("게시물이 존재하지 않습니다.");
    }

    public Header<Page<FreeBoardPageResponse>> searchByCond(FreeBoardSearchCond cond, Pageable pageable) {
        return Header.OK(freeBoardRepository.searchByCond(cond, pageable));
    }


}
