package com.lotto.web.model.entity;

import com.lotto.web.constants.BoardActivationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "board")
public class BoardEntity extends AbstractBoardBaseEntity {
    private String name;

    @Enumerated(EnumType.STRING)
    private BoardActivationStatus status;

    @PrePersist
    public void onPrevisionPersist() {
        this.status = BoardActivationStatus.NORMAL;
    }
}
