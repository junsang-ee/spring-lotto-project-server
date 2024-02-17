package com.lotto.web.model.entity.admin;

import com.lotto.web.constants.SettingToggleType;
import com.lotto.web.model.SequentialEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "admin_setting")
public class AdminSettingEntity extends SequentialEntity {

    @Enumerated(EnumType.STRING)
    private SettingToggleType lottoAutoUpdateToggle;
}
