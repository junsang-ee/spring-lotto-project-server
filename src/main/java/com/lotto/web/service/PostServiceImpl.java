package com.lotto.web.service;

import com.lotto.web.constants.*;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.InvalidStateException;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.request.PostSaveRequest;
import com.lotto.web.model.dto.request.PostUpdateRequest;
import com.lotto.web.model.dto.response.PostDetailResponse;
import com.lotto.web.model.dto.response.PostListEntryResponse;
import com.lotto.web.model.dto.response.PostSaveResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.model.entity.PostEntity;
import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.model.entity.count.ReplyCountEntity;
import com.lotto.web.repository.PostRepository;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final BoardService boardService;

    private final UserService userService;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public PostSaveResponse save(String userId, String boardId, PostSaveRequest request) {
        PostEntity entity = new PostEntity();
        setPostEntity(userId, boardId, entity, request);
        PostEntity savedPost = postRepository.save(entity);
        PostSaveResponse result = new PostSaveResponse();
        setPostSaveResponse(result, savedPost);
        return result;
    }

    @Override
    @Transactional
    public boolean delete(String userId, String postId) {
        PostEntity post = get(postId);
        validPost(post, null, MethodType.DELETE, userId);
        post.setStatus(PostActivationStatus.REMOVED);
        postRepository.save(post);
        return true;
    }

    @Override
    @Transactional
    public boolean update(String userId, String postId, PostUpdateRequest request) {
        PostEntity post = get(postId);
        validPost(post, null, MethodType.UPDATE, userId);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        postRepository.save(post);
        return true;
    }

    @Override
    public PostDetailResponse detail(String userId, String postId) {
        PostEntity post = get(postId);
        validPost(post, null, MethodType.GET, null);
        PostDetailResponse result = new PostDetailResponse();
        setPostDetail(userId, post, result);
        return result;
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
    public Page<PostListEntryResponse> list(String boardId, Pageable pageable) {
        BoardEntity parentBoard = boardService.get(boardId);
        Page<PostListEntryResponse> list =
                postRepository.findAllByParentBoardAndStatus(
                        PostActivationStatus.NORMAL,
                        parentBoard,
                        pageable
                );
        return new PageImpl<>(
                list.stream().collect(Collectors.toList()),
                list.getPageable(),
                list.getTotalElements());
    }

    @Override
    public boolean verifyPassword(String postId, String password) {
        PostEntity post = get(postId);
        validPost(post, password, MethodType.GET, null);
        return true;
    }

    @Override
    @Transactional
    public void updateReplyCount(PostEntity post, ReplyCountEntity replyCount) {
        post.setReplyCount(replyCount);
        postRepository.save(post);
    }

    private void setPostEntity(String userId, String boardId, PostEntity entity, PostSaveRequest request) {
        entity.setParentBoard(boardService.get(boardId));
        entity.setCreatedBy(userService.getUser(userId));
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setDisclosureType(request.getDisclosureType());
        if (request.getPassword() != null &&
            request.getDisclosureType() == PostDisclosureType.PRIVATE) {
            entity.setPassword(passwordEncoder.encode(request.getPassword()));
        } else entity.setPassword(null);
    }

    private void setPostDetail(String userId, PostEntity post, PostDetailResponse response) {
        UserEntity createdBy = post.getCreatedBy();
        response.setWriter(createdBy.getEmail());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setMine(createdBy == userService.getUser(userId));
        response.setDisclosureType(post.getDisclosureType());
        response.setReplyCount(post.getReplyCount().getEnabledCount());
    }

    private void setPostSaveResponse(PostSaveResponse result, PostEntity post) {
        result.setId(post.getId());
        result.setTitle(post.getTitle());
        result.setContent(post.getContent());
        result.setViewCount(post.getViewCount());
        result.setStatus(post.getStatus());
        result.setDisclosureType(post.getDisclosureType());
        result.setWriter(post.getCreatedBy().getEmail());
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
