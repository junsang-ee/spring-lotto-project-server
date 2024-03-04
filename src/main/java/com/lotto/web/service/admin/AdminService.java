package com.lotto.web.service.admin;

import com.lotto.web.constants.BoardActivationStatus;
import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.constants.UserStatus;
import com.lotto.web.model.dto.request.BoardSaveRequest;
import com.lotto.web.model.dto.request.SettingUpdateRequest;
import com.lotto.web.model.dto.response.admin.BoardManageDetailResponse;
import com.lotto.web.model.dto.response.admin.PostManageDetailResponse;
import com.lotto.web.model.dto.response.admin.UserManageDetailResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.model.entity.PostEntity;
import com.lotto.web.model.entity.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {

    BoardEntity saveBoard(BoardSaveRequest request);

    boolean deleteBoard(String boardId);

    void deletePost(String postId);

    void createAdminSetting();
    void createAdminAccount();

    void updateLottoAutomationSetting(SettingUpdateRequest toggle);

    Page<BoardManageDetailResponse> getBoardList(Pageable pageable);
    Page<UserManageDetailResponse> getUserList(Pageable pageable);

    Page<PostManageDetailResponse> getPostList(String boardId, Pageable pageable);

    UserEntity getUserDetail(String userId);

    PostEntity getPostDetail(String postId);

    void updateUserStatus(String userId, UserStatus status);

    void updateBoardStatus(String boardId, BoardActivationStatus status);

    void updatePostStatus(String postId, PostActivationStatus status);

    UserEntity getAdmin();

    BoardEntity getBoardDetail(String boardId);
}
