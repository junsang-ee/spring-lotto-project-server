package com.lotto.web.service;

import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.constants.PostDisclosureType;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.request.PostSaveRequest;
import com.lotto.web.model.dto.response.PostListEntryResponse;
import com.lotto.web.model.entity.PostEntity;
import com.lotto.web.model.querydsl.PostListQueryResult;
import com.lotto.web.repository.PostRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final BoardService boardService;

    private final UserService userService;

    @Override
    @Transactional
    public boolean save(String userId, String boardId, PostSaveRequest request) {
        PostEntity entity = new PostEntity();
        setPostEntity(userId, boardId, entity, request);
        postRepository.save(entity);
        return true;
    }

    @Override
    public PostEntity get(String postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.POST_NOT_FOUND)
        );
    }

    @Override
    public List<PostListEntryResponse> list(String boardId, Pageable pageable) {
        List<PostListQueryResult> result =
                postRepository.findAllByParentBoardAndStatus(
                        PostActivationStatus.NORMAL,
                        boardId
                );
        return result.stream().map(PostListEntryResponse::new).collect(Collectors.toList());
    }

    private void setPostEntity(String userId, String boardId, PostEntity entity, PostSaveRequest request) {
        entity.setParentBoard(boardService.get(boardId));
        entity.setCreatedBy(userService.getUser(userId));
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setDisclosureType(request.getDisclosureType());
        if (request.getDisclosureType() == PostDisclosureType.PRIVATE) {
            entity.setPassword(request.getPassword());
        } else entity.setPassword(null);
    }


}
