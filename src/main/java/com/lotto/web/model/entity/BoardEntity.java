package com.lotto.web.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lotto.web.constants.BoardAccessType;
import com.lotto.web.constants.BoardActivationStatus;
import com.lotto.web.model.entity.count.PostCountEntity;
import com.lotto.web.model.entity.count.ReplyCountEntity;
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
public class BoardEntity extends CreationUserEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardActivationStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardAccessType accessType;

    @JsonIgnore
    @OneToMany(mappedBy = "parentBoard", fetch = FetchType.LAZY)
    private List<PostEntity> posts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_count")
    private PostCountEntity postCount;

    @PrePersist
    public void onPrevisionPersist() {
        this.postCount = new PostCountEntity();
        this.status = BoardActivationStatus.NORMAL;
    }
}
