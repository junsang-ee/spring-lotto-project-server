package com.lotto.web.model.entity;

import com.lotto.web.constants.PostDisclosureType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "post")
@Entity(name = "post")
public class PostEntity extends AbstractPostEntity {

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "Text")
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


    @PrePersist
    public void onPrevisionPersist() {
        this.viewCount = 0;
    }
}
