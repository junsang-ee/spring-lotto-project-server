package com.lotto.web.model.entity;

import com.lotto.web.constants.PostActivationStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@MappedSuperclass
public abstract class AbstractPostEntity extends CreationUserEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostActivationStatus status;

}
