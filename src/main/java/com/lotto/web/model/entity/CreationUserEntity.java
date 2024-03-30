package com.lotto.web.model.entity;

import com.lotto.web.model.ModificationTimestampEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public class CreationUserEntity extends ModificationTimestampEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private UserEntity createdBy;

    protected CreationUserEntity(final UserEntity user) {
        this.createdBy = user;
    }
}
