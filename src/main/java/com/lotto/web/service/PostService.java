package com.lotto.web.service;

import com.lotto.web.model.dto.request.PostSaveRequest;
import com.lotto.web.model.dto.request.PostUpdateRequest;
import com.lotto.web.model.dto.response.PostDetailResponse;
import com.lotto.web.model.dto.response.PostListEntryResponse;
import com.lotto.web.model.entity.PostEntity;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {
    boolean save(String userId, String boardId, PostSaveRequest request);

    boolean update(String userId, String postId, PostUpdateRequest request);

    boolean delete(String userId, String postId);

    PostEntity get(String postId);

    PostDetailResponse detail(String userId, String postId, String password);

    List<PostListEntryResponse> list(String boardId, Pageable pageable);
}
