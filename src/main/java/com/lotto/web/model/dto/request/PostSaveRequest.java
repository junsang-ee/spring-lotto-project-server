package com.lotto.web.model.dto.request;

import com.lotto.web.constants.PostDisclosureType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSaveRequest {
    private String title;

    private String content;

    private PostDisclosureType disclosureType;

    private String password;

}
