package com.lotto.web.model.dto.response;

import com.lotto.web.constants.PostDisclosureType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDetailResponse {

    private String writer;

    private String title;

    private String content;

    private boolean mine;

    private PostDisclosureType disclosureType;

    private int replyCount;

}
