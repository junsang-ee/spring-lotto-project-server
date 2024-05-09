package com.lotto.web.service;

import com.lotto.web.constants.*;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.InvalidStateException;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.request.PostSaveRequest;
import com.lotto.web.model.dto.request.PostUpdateRequest;
import com.lotto.web.model.dto.response.PostDetailResponse;
import com.lotto.web.model.dto.response.PostListResponse;
import com.lotto.web.model.dto.response.PostSaveResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.model.entity.PostEntity;
import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.repository.BoardRepository;
import com.lotto.web.repository.PostRepository;

import com.lotto.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public PostSaveResponse save(String userId, String boardId, PostSaveRequest request) {
        PostEntity post = PostEntity.of(
                getUser(userId),
                getParentBoard(boardId),
                request
        );
        return PostSaveResponse.of(postRepository.save(post));
    }

    @Override
    @Transactional
    public boolean delete(String userId, String postId) {
        PostEntity post = get(postId);
        validPost(post, null, MethodType.DELETE, userId);
        post.updateStatus(PostActivationStatus.REMOVED);
        return true;
    }

    @Override
    @Transactional
    public boolean update(String userId, String postId, PostUpdateRequest request) {
        PostEntity post = get(postId);
        validPost(post, null, MethodType.UPDATE, userId);
        post.update(request);
        return true;
    }

    @Override
    public PostDetailResponse detail(String userId, String postId) {
        PostEntity post = get(postId);
        validPost(post, null, MethodType.GET, null);
        return PostDetailResponse.of(userId, post);
    }

    @Override
    public PostDetailResponse detailForAdmin(String postId) {
        PostEntity post = get(postId);
        return null;
    }

    @Override
    public PostEntity get(String postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.POST_NOT_FOUND)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostListResponse> list(String boardId, Pageable pageable) {
        BoardEntity parentBoard = getParentBoard(boardId);
        return postRepository.findAllByParentBoardAndStatus(
                parentBoard,
                PostActivationStatus.NORMAL,
                pageable
        ).map(PostListResponse::of);
    }

    @Override
    public boolean verifyPassword(String postId, String password) {
        PostEntity post = get(postId);
        validPost(post, password, MethodType.GET, null);
        return true;
    }

    private BoardEntity getParentBoard(String boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BOARD_NOT_FOUND)
        );
    }

    private UserEntity getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );
    }

    private void validPost(PostEntity post, String password, MethodType type, String userId) {
        switch (post.getStatus()) {
            case REMOVED:
                throw new InvalidStateException(ErrorMessage.POST_REMOVED);
            case DISABLED:
                throw new InvalidStateException(ErrorMessage.POST_DISABLED);
            case NORMAL:
                if (type == MethodType.GET) {
                    if (password != null && post.getDisclosureType() == PostDisclosureType.PRIVATE) {
                        if (!passwordEncoder.matches(password, post.getPassword()))
                            throw new InvalidStateException(ErrorMessage.POST_INVALID_PASSWORD);
                    }
                } else {
                    UserEntity user = userService.getUser(userId);
                    if (user.getRole() == UserRole.ADMIN) break;
                    if (user != post.getCreatedBy()) {
                        if (type == MethodType.DELETE)
                            throw new InvalidStateException(ErrorMessage.POST_ONLY_REMOVE_WRITER);
                        throw new InvalidStateException(ErrorMessage.POST_ONLY_EDIT_WRITER);
                    }
                }
                break;
            default:
                throw new InvalidStateException(ErrorMessage.UNKNOWN);

        }
    }


}
