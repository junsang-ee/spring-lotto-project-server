package com.lotto.web.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractBoardBaseEntity extends CreationTimestampEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;
}
