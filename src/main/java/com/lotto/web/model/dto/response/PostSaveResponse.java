package com.lotto.web.model.dto.response;

import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.constants.PostDisclosureType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostSaveResponse {
    private String id;
    private String title;
    private String content;
    private int viewCount;
    private PostActivationStatus status;
    private PostDisclosureType disclosureType;
    private String writer;
}
