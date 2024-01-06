package com.lotto.web.service;

import com.lotto.web.constants.MethodType;
import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.InvalidStateException;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.request.ReplyUpdateRequest;
import com.lotto.web.model.entity.ReplyEntity;
import com.lotto.web.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    private final UserService userService;

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
        reply.setContent(request.getContent());
        replyRepository.save(reply);
        return true;
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
