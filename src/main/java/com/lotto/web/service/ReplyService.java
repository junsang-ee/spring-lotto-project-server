package com.lotto.web.service;

import com.lotto.web.model.dto.request.ReplySaveRequest;
import com.lotto.web.model.dto.request.ReplyUpdateRequest;
import com.lotto.web.model.entity.ReplyEntity;

public interface ReplyService {

    boolean save(String userId, String postId, ReplySaveRequest request);
    ReplyEntity get(String replyId);

    boolean update(String userId, String replyId, ReplyUpdateRequest request);

    boolean delete(String userId, String replyId);
}
