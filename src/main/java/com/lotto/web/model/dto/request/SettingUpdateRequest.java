package com.lotto.web.model.dto.request;

import com.lotto.web.constants.SettingToggleType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SettingUpdateRequest {
    private SettingToggleType type;
}
