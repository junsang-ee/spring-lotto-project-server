package com.lotto.web.model.entity;

import com.lotto.web.constants.BoardAccessType;
import com.lotto.web.constants.BoardActivationStatus;
import com.lotto.web.model.ModificationTimestampEntity;
import com.lotto.web.model.dto.request.BoardSaveRequest;
import com.lotto.web.model.entity.count.PostCountEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "board")
@Entity(name = "board")
public class BoardEntity extends ModificationTimestampEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardActivationStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardAccessType accessType;

    @OneToMany(mappedBy = "parentBoard",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private List<PostEntity> posts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_count")
    private PostCountEntity postCount;

    public static BoardEntity of(final BoardSaveRequest saveRequest) {
        return new BoardEntity(
                saveRequest.getName(),
                saveRequest.getAccessType()
        );
    }

    protected BoardEntity(String name, BoardAccessType type) {
        this.name = name;
        this.accessType = type;
        this.status = BoardActivationStatus.NORMAL;
        this.postCount = PostCountEntity.of();
    }

    public void updateStatus(BoardActivationStatus status) {
        this.status = status;
    }
}
