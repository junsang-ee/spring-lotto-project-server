package com.lotto.web.model.entity;

import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.constants.PostDisclosureType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "post")
@Entity(name = "post")
public class PostEntity extends AbstractPostEntity {

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostDisclosureType disclosureType;

    private String password;

    @Column(nullable = false)
    private int viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentBoard", nullable = false)
    private BoardEntity parentBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer")
    private UserEntity writer;

    @OneToMany(mappedBy = "parentPost", fetch = FetchType.LAZY)
    private List<ReplyEntity> replies;

    @PrePersist
    public void onPrevisionPersist() {
        this.viewCount = 0;
        super.setStatus(PostActivationStatus.NORMAL);
    }
}
