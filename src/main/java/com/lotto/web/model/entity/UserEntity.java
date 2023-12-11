package com.lotto.web.model.entity;

import com.lotto.web.constants.UserRole;
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
@Table(name = "user")
public class UserEntity extends CreationTimestampEntity {
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
