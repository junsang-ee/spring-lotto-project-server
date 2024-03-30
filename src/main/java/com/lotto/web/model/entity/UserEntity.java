package com.lotto.web.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lotto.web.constants.UserRole;
import com.lotto.web.constants.UserStatus;
import com.lotto.web.model.CreationTimestampEntity;
import com.lotto.web.model.dto.request.SignupRequest;
import com.lotto.web.model.entity.lotto.ExtractionHistoryEntity;
import com.lotto.web.util.EncryptUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
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
    private int dailyAvailableCount;

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<PostEntity> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<ReplyEntity> replies;

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<ExtractionHistoryEntity> extractionLottoList;

    public static UserEntity of(final SignupRequest signupRequest,
                                final UserRole role) {
        return new UserEntity(
                signupRequest.getEmail(),
                signupRequest.getPassword(),
                role
        );
    }

    protected UserEntity(final String email,
                         final String password,
                         final UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = UserStatus.ENABLED;
        this.dailyAvailableCount = 100;
    }

    public void updateStatus(UserStatus status) {
        this.status = status;
    }

    public void updatePassword(String password) {
        this.password = EncryptUtil.encode(password);
    }

    public void updateAvailableCount(int count) {
        this.dailyAvailableCount -= count;
    }

}
