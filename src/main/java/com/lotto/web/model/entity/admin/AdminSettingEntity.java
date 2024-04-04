package com.lotto.web.model.entity.admin;

import com.lotto.web.constants.SettingToggleType;
import com.lotto.web.model.SequentialEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Table
@Entity(name = "admin_setting")
public class AdminSettingEntity extends SequentialEntity {

    @Enumerated(EnumType.STRING)
    private SettingToggleType lottoAutoUpdateToggle;

    public static AdminSettingEntity of(final SettingToggleType type) {
        return new AdminSettingEntity(type);
    }


    protected AdminSettingEntity(SettingToggleType type) {
        this.lottoAutoUpdateToggle = type;
    }

    public void update(SettingToggleType type) {
        this.lottoAutoUpdateToggle = type;
    }
}
