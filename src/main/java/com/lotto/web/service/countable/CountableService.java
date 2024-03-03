package com.lotto.web.service.countable;

import com.lotto.web.constants.countable.CountableType;
import com.lotto.web.model.entity.count.ReplyCountEntity;

public interface CountableService {
    ReplyCountEntity getReplyCount(String postId);

    void updateReplyCount(String postId, CountableType type);

    void updatePostCount(String boardId, CountableType type);
}
