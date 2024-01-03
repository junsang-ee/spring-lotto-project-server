package com.lotto.web.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lotto.web.constants.PostActivationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "reply")
@Entity(name = "reply")
public class ReplyEntity extends AbstractPostEntity {

    @Column(nullable = false)
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentPost", nullable = false)
    private PostEntity parentPost;

    @PrePersist
    public void onPrevisionPersist() {
        super.setStatus(PostActivationStatus.NORMAL);
    }
}
