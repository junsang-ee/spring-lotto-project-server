package com.lotto.web.model.entity;

import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.constants.PostDisclosureType;
import com.lotto.web.model.dto.request.PostSaveRequest;
import com.lotto.web.model.dto.request.PostUpdateRequest;
import com.lotto.web.model.entity.count.ReplyCountEntity;
import com.lotto.web.util.EncryptUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
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

    @OneToMany(mappedBy = "parentPost", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ReplyEntity> replies;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reply_count")
    private ReplyCountEntity replyCount;

    public static PostEntity of(final UserEntity user,
                                final BoardEntity board,
                                final PostSaveRequest saveRequest) {
        String encodedPassword = null;
        if (saveRequest.getPassword() != null &&
            saveRequest.getDisclosureType() == PostDisclosureType.PRIVATE) {
            encodedPassword = EncryptUtil.encode(saveRequest.getPassword());
        }
        return new PostEntity(
                user,
                board,
                saveRequest.getTitle(),
                saveRequest.getContent(),
                saveRequest.getDisclosureType(),
                encodedPassword
        );
    }

    protected PostEntity(UserEntity user, BoardEntity board, String title, String content,
                       PostDisclosureType disclosureType, String password) {
        super(PostActivationStatus.NORMAL, user);
        this.parentBoard = board;
        this.title = title;
        this.content = content;
        this.disclosureType = disclosureType;
        this.password = password;
        this.viewCount = 0;
        this.replyCount = ReplyCountEntity.of();
    }

    public void update(final PostUpdateRequest updateRequest) {
        this.title = updateRequest.getTitle();
        this.content = updateRequest.getContent();
    }

}
