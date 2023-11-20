package com.lotto.web.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity extends CreationTimestampEntity {
    private String name;
    private String email;
    private String password;
}
