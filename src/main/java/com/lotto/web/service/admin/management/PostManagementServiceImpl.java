package com.lotto.web.service.admin.management;

import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.InvalidStateException;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.response.PostDeleteResponse;
import com.lotto.web.model.dto.response.admin.PostManageDetailResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.model.entity.PostEntity;
import com.lotto.web.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostManagementServiceImpl implements PostManagementService {

    private final PostRepository postRepository;

    private final BoardManagementService boardManagementService;
    @Override
    public PostEntity get(String postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.POST_NOT_FOUND)
        );
    }

    @Override
    public Page<PostManageDetailResponse> list(String boardId, Pageable pageable) {
        BoardEntity parentBoard = boardManagementService.get(boardId);
        Page<PostManageDetailResponse> list = postRepository.getAllPost(parentBoard, pageable);
        return new PageImpl<>(
                list.stream().collect(Collectors.toList()),
                list.getPageable(),
                list.getTotalElements()
        );
    }

    @Override
    @Transactional
    public boolean updateStatus(String postId, PostActivationStatus status) {
        PostEntity post = get(postId);
        validStatus(post, status);
        post.setStatus(status);
        postRepository.save(post);
        return true;
    }

    @Override
    @Transactional
    public PostDeleteResponse delete(String postId) {
        PostEntity post = get(postId);
        postRepository.delete(post);
        PostDeleteResponse result = new PostDeleteResponse();
        result.setTitle(post.getTitle());
        return result;
    }

    private void validStatus(PostEntity post, PostActivationStatus status) {
        if (post.getStatus() == status) {
            switch (post.getStatus()) {
                case NORMAL:
                    throw new InvalidStateException(ErrorMessage.POST_ENABLED);
                case REMOVED:
                    throw new InvalidStateException(ErrorMessage.POST_DISABLED);
                case DISABLED:
                    throw new InvalidStateException(ErrorMessage.POST_REMOVED);
            }
        }
    }
}
