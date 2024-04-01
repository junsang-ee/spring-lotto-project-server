package com.lotto.web.service;

import com.lotto.web.constants.MethodType;
import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.InvalidStateException;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.request.ReplySaveRequest;
import com.lotto.web.model.dto.request.ReplyUpdateRequest;
import com.lotto.web.model.dto.response.ReplyDetailResponse;
import com.lotto.web.model.dto.response.ReplySaveResponse;
import com.lotto.web.model.entity.PostEntity;
import com.lotto.web.model.entity.ReplyEntity;
import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.repository.PostRepository;
import com.lotto.web.repository.ReplyRepository;

import com.lotto.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public ReplySaveResponse save(String userId, String postId, ReplySaveRequest request) {
        PostEntity parentPost = getParentPost(postId);
        UserEntity user = getUser(userId);
        ReplyEntity reply = ReplyEntity.of(
                user,
                parentPost,
                request
        );
        return ReplySaveResponse.of(replyRepository.save(reply));
    }

    @Override
    public ReplyEntity get(String replyId) {
        return replyRepository.findById(replyId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.REPLY_NOT_FOUND)
        );
    }

    @Override
    @Transactional
    public boolean update(String userId, String replyId, ReplyUpdateRequest request) {
        ReplyEntity reply = get(replyId);
        valid(reply, MethodType.UPDATE, userId);
        reply.update(request.getContent());
        return true;
    }

    @Override
    @Transactional
    public boolean delete(String userId, String replyId) {
        ReplyEntity reply = get(replyId);
        valid(reply, MethodType.DELETE, userId);
        reply.updateStatus(PostActivationStatus.REMOVED);
        return true;
    }

    @Override
    public List<ReplyDetailResponse> list(String userId,
                                          String postId,
                                          Pageable pageable) {
        List<ReplyEntity> replies = replyRepository.findAllByParentPostAndStatus(
                getParentPost(postId),
                PostActivationStatus.NORMAL,
                pageable
        );
        return replies.stream()
                .map(reply -> ReplyDetailResponse.of(reply, userId))
                .collect(Collectors.toList());
    }

    private PostEntity getParentPost(String postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.POST_NOT_FOUND)
        );
    }

    private UserEntity getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );
    }

    private void valid(ReplyEntity reply, MethodType type, String userId) {
                switch (reply.getStatus()) {
            case DISABLED:
                throw new InvalidStateException(ErrorMessage.REPLY_DISABLED);
            case REMOVED:
                throw new InvalidStateException(ErrorMessage.REPLY_REMOVED);
            case NORMAL:
                if (getUser(userId) != reply.getCreatedBy()) {
                    if (type == MethodType.DELETE)
                        throw new InvalidStateException(ErrorMessage.REPLY_ONLY_REMOVE_WRITER);
                    else if (type == MethodType.UPDATE)
                        throw new InvalidStateException(ErrorMessage.REPLY_ONLY_EDIT_WRITER);
                }
                break;
            default:
                throw new InvalidStateException(ErrorMessage.UNKNOWN);
        }
    }
}
