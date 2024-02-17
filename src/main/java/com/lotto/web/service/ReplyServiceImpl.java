package com.lotto.web.service;

import com.lotto.web.constants.MethodType;
import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.InvalidStateException;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.request.ReplySaveRequest;
import com.lotto.web.model.dto.request.ReplyUpdateRequest;
import com.lotto.web.model.dto.response.ReplyDetailResponse;
import com.lotto.web.model.entity.ReplyEntity;
import com.lotto.web.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    private final UserService userService;

    private final PostService postService;

    @Override
    @Transactional
    public ReplyEntity save(String userId, String postId, ReplySaveRequest request) {
        ReplyEntity reply = new ReplyEntity();
        setSaveReply(userId, postId, reply, request);
        return replyRepository.save(reply);
    }

    @Override
    public ReplyEntity get(String replyId) {
        return replyRepository.findById(replyId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.REPLY_NOT_FOUND)
        );
    }

    @Override
    @Transactional
    public ReplyEntity update(String userId, String replyId, ReplyUpdateRequest request) {
        ReplyEntity reply = get(replyId);
        valid(reply, MethodType.UPDATE, userId);
        reply.setContent(request.getContent());
        return replyRepository.save(reply);
    }

    @Override
    @Transactional
    public boolean delete(String userId, String replyId) {
        ReplyEntity reply = get(replyId);
        valid(reply, MethodType.DELETE, userId);
        reply.setStatus(PostActivationStatus.REMOVED);
        replyRepository.save(reply);
        return true;
    }

    @Override
    public List<ReplyDetailResponse> listForUser(String userId,
                                                 String postId,
                                                 Pageable pageable) {
        return replyRepository.findAllByParentPostAndStatus(
                userService.getUser(userId),
                PostActivationStatus.NORMAL,
                postService.get(postId),
                pageable
        );
    }

    private void setSaveReply(String userId,
                              String postId,
                              ReplyEntity entity,
                              ReplySaveRequest request) {
        entity.setContent(request.getContent());
        entity.setCreatedBy(userService.getUser(userId));
        entity.setParentPost(postService.get(postId));
    }

    private void valid(ReplyEntity reply, MethodType type, String userId) {
                switch (reply.getStatus()) {
            case DISABLED:
                throw new InvalidStateException(ErrorMessage.REPLY_DISABLED);
            case REMOVED:
                throw new InvalidStateException(ErrorMessage.REPLY_REMOVED);
            case NORMAL:
                if (userService.getUser(userId) != reply.getCreatedBy()) {
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
