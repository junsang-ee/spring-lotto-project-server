package com.lotto.web.model.dto.response.admin;

import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.constants.PostDisclosureType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class PostManageListResponse {

    private String id;

    private String title;

    private String writer;

    private PostDisclosureType disclosureType;

    private PostActivationStatus status;

    private int enabledReplyCount;

    private int disabledReplyCount;

    private Instant createdAt;
}
