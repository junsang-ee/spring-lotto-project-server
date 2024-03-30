package com.lotto.web.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.model.dto.request.ReplySaveRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "reply")
@Entity(name = "reply")
public class ReplyEntity extends AbstractPostEntity {

    @Column(nullable = false)
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentPost", nullable = false)
    private PostEntity parentPost;

    public static ReplyEntity of(final UserEntity user,
                                 final PostEntity parentPost,
                                 final ReplySaveRequest saveRequest) {
        return new ReplyEntity(
                user,
                parentPost,
                saveRequest.getContent()
        );

    }
    protected ReplyEntity(UserEntity user, PostEntity post, String content) {
        super(PostActivationStatus.NORMAL, user);
        this.parentPost = post;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }

}
