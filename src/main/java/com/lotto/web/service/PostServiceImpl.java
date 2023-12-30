package com.lotto.web.service;

import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.request.PostSaveRequest;
import com.lotto.web.model.dto.response.PostListEntryResponse;
import com.lotto.web.model.dto.response.PostListResponse;
import com.lotto.web.model.dto.response.common.PageResponse;
import com.lotto.web.model.entity.PostEntity;
import com.lotto.web.model.querydsl.PostListQueryResult;
import com.lotto.web.repository.PostRepository;
import com.lotto.web.repository.criteria.PostCriteria;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final BoardService boardService;

    @Override
    @Transactional
    public boolean save(String boardId, PostSaveRequest request) {
        PostEntity entity = new PostEntity();
        setPostEntity(boardId, entity, request);
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

    private void setPostEntity(String boardId, PostEntity entity, PostSaveRequest request) {
        entity.setParentBoard(boardService.get(boardId));
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setDisclosureType(request.getPostDisclosureType());
        entity.setPassword(request.getPassword());
    }


}
