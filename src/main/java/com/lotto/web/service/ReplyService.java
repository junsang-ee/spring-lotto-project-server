package com.lotto.web.service;

import com.lotto.web.model.dto.request.ReplySaveRequest;
import com.lotto.web.model.dto.request.ReplyUpdateRequest;
import com.lotto.web.model.dto.response.ReplyDetailResponse;
import com.lotto.web.model.entity.ReplyEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReplyService {

    ReplyEntity save(String userId, String postId, ReplySaveRequest request);
    ReplyEntity get(String replyId);

    ReplyEntity update(String userId, String replyId, ReplyUpdateRequest request);

    boolean delete(String userId, String replyId);

    List<ReplyDetailResponse> listForUser(String userId, String postId, Pageable pageable);
}
