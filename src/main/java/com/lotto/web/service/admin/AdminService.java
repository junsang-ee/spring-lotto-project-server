package com.lotto.web.service.admin;

import com.lotto.web.constants.UserStatus;
import com.lotto.web.model.dto.request.BoardSaveRequest;
import com.lotto.web.model.dto.request.SettingUpdateRequest;
import com.lotto.web.model.dto.response.admin.BoardDetailResponse;
import com.lotto.web.model.dto.response.admin.UserDetailResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.model.entity.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AdminService {

    BoardEntity saveBoard(BoardSaveRequest request);

    boolean deleteBoard(String boardId);

    void createAdminSetting();
    void createAdminAccount();

    void updateLottoAutomationSetting(SettingUpdateRequest toggle);

    List<BoardDetailResponse> getBoardList();
    Page<UserDetailResponse> getUserList(Pageable pageable);

    UserEntity getUserDetail(String userId);

    void updateUserStatus(String userId, UserStatus status);

    UserEntity getAdmin();
}
