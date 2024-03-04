package com.lotto.web.service.admin.management;

import com.lotto.web.constants.BoardActivationStatus;
import com.lotto.web.model.dto.request.BoardSaveRequest;
import com.lotto.web.model.dto.response.BoardDeleteResponse;
import com.lotto.web.model.dto.response.BoardSaveResponse;
import com.lotto.web.model.dto.response.admin.BoardManageDetailResponse;
import com.lotto.web.model.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardManagementService {

    BoardEntity get(String boardId);
    BoardSaveResponse save(BoardSaveRequest request);

    BoardDeleteResponse delete(String boardId);

    boolean updateStatus(String boardId, BoardActivationStatus status);

    Page<BoardManageDetailResponse> list(Pageable pageable);


}
