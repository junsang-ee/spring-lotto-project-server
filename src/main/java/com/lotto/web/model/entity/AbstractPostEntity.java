package com.lotto.web.model.entity;

import com.lotto.web.constants.PostActivationStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@MappedSuperclass
public class AbstractPostEntity extends CreationUserEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostActivationStatus status;

    protected AbstractPostEntity(PostActivationStatus status, UserEntity user) {
        super(user);
        this.status = status;
    }

    public void updateStatus(PostActivationStatus status) {
        this.status = status;
    }
}
