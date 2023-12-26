package com.lotto.web.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lotto.web.constants.BoardAccessType;
import com.lotto.web.constants.BoardActivationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "board")
@Entity(name = "board")
public class BoardEntity extends AbstractBoardBaseEntity {
    private String name;

    @Enumerated(EnumType.STRING)
    private BoardActivationStatus status;

    @Enumerated(EnumType.STRING)
    private BoardAccessType accessType;

    @JsonIgnore
    @OneToMany(mappedBy = "parentBoard", fetch = FetchType.LAZY)
    private List<PostEntity> posts;

    @PrePersist
    public void onPrevisionPersist() {
        this.status = BoardActivationStatus.NORMAL;
    }
}
