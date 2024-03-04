package com.lotto.web.service.admin.management;

import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.model.dto.response.PostDeleteResponse;
import com.lotto.web.model.dto.response.admin.PostManageDetailResponse;
import com.lotto.web.model.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostManagementService {

    PostEntity get(String postId);
    Page<PostManageDetailResponse> list(String boardId, Pageable pageable);

    boolean updateStatus(String postId, PostActivationStatus status);

    PostDeleteResponse delete(String postId);
}
