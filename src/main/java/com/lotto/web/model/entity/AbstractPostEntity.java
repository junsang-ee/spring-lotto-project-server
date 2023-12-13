package com.lotto.web.model.entity;

import com.lotto.web.constants.PostActivationStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractPostEntity extends AbstractBoardBaseEntity {

    @Enumerated(EnumType.STRING)
    private PostActivationStatus status;

    @PrePersist
    public void onPrevisionPersist() {
        this.status = PostActivationStatus.NORMAL;
    }
}
