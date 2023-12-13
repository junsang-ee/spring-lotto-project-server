package com.lotto.web.model.entity;

import com.lotto.web.config.BoardActivation;
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
@Table(name = "board")
public class BoardEntity extends CreationTimestampEntity {
    private String name;

    @Enumerated(EnumType.STRING)
    private BoardActivation activation;
}
