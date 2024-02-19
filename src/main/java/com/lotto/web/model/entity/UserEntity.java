package com.lotto.web.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lotto.web.constants.UserRole;
import com.lotto.web.constants.UserStatus;
import com.lotto.web.model.CreationTimestampEntity;
import com.lotto.web.model.entity.lotto.ExtractionHistoryEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "\"user\"")
@Entity(name = "user")
public class UserEntity extends CreationTimestampEntity {

    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Max(100)
    private int dailyAvailableCount = 100;

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<PostEntity> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<BoardEntity> boards;

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<ReplyEntity> replies;

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<ExtractionHistoryEntity> extractionLottoList;

    @PrePersist
    private void onPrevisionPersist() {
        this.status = UserStatus.ENABLED;
    }

}
