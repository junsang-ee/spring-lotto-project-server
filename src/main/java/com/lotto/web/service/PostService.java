package com.lotto.web.service;

import com.lotto.web.model.dto.request.PostSaveRequest;
import com.lotto.web.model.dto.response.PostListEntryResponse;
import com.lotto.web.model.dto.response.PostListResponse;
import com.lotto.web.model.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    boolean save(String userId, String boardId, PostSaveRequest request);

    PostEntity get(String PostId);

    List<PostListEntryResponse> list(String boardId, Pageable pageable);
}
