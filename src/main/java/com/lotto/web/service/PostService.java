package com.lotto.web.service;

import com.lotto.web.model.dto.request.PostSaveRequest;
import com.lotto.web.model.dto.request.PostUpdateRequest;
import com.lotto.web.model.dto.response.PostDetailResponse;
import com.lotto.web.model.dto.response.PostListEntryResponse;
import com.lotto.web.model.dto.response.PostSaveResponse;
import com.lotto.web.model.entity.PostEntity;

import com.lotto.web.model.entity.count.ReplyCountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostSaveResponse save(String userId, String boardId, PostSaveRequest request);

    boolean update(String userId, String postId, PostUpdateRequest request);

    boolean delete(String userId, String postId);

    PostEntity get(String postId);

    PostDetailResponse detail(String userId, String postId);

    PostDetailResponse detailForAdmin(String postId);

    Page<PostListEntryResponse> list(String boardId, Pageable pageable);

    boolean verifyPassword(String postId, String password);

    void updateReplyCount(PostEntity post, ReplyCountEntity replyCount);
}
