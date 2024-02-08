package com.lotto.web.constants.cache;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@NoArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum CacheType {

    @FieldNameConstants.Include USER_BOARDS("USER_BOARDS", 60 * 24),
    @FieldNameConstants.Include SIGNUP_CODE("SIGNUP_CODE", 3);


    private String name;

    private int expiredTime;

    private int maximumSize;

    CacheType(String name, int expiredTime) {
        this.name = name;
        this.expiredTime = expiredTime;
        this.maximumSize = 10000;
    }

}
