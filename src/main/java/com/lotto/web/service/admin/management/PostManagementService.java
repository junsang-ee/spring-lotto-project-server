package com.lotto.web.service.admin.management;

import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.model.dto.response.PostDeleteResponse;
import com.lotto.web.model.dto.response.admin.PostManageListResponse;
import com.lotto.web.model.dto.response.admin.UserPostListResponse;
import com.lotto.web.model.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostManagementService {

    PostEntity get(String postId);
    Page<PostManageListResponse> list(String boardId, Pageable pageable);

    boolean updateStatus(String postId, PostActivationStatus status);

    PostDeleteResponse delete(String postId);

    Page<UserPostListResponse> listByUser(String userId, Pageable pageable);
}
