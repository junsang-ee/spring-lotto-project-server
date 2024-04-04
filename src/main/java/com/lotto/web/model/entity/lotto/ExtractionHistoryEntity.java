package com.lotto.web.model.entity.lotto;

import com.lotto.web.model.TimestampSequentialEntity;
import com.lotto.web.model.dto.response.DefaultLottoResponse;
import com.lotto.web.model.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "extraction_history")
@Entity(name = "extraction_history")
public class ExtractionHistoryEntity extends TimestampSequentialEntity {
    private int firstNumber;
    private int secondNumber;
    private int thirdNumber;
    private int fourthNumber;
    private int fifthNumber;
    private int sixthNumber;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "winning_status")
    private WinningStatusEntity winningStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private UserEntity createdBy;

    public static ExtractionHistoryEntity of(final DefaultLottoResponse lotto,
                                             final UserEntity user) {
        return new ExtractionHistoryEntity(
                lotto, user
        );
    }

    protected ExtractionHistoryEntity(DefaultLottoResponse lotto, UserEntity user) {
        this.firstNumber = lotto.getFirstNumber();
        this.secondNumber = lotto.getSecondNumber();
        this.thirdNumber = lotto.getThirdNumber();
        this.fourthNumber = lotto.getFourthNumber();
        this.fifthNumber = lotto.getFifthNumber();
        this.sixthNumber = lotto.getSixthNumber();
        this.createdBy = user;
        this.winningStatus = WinningStatusEntity.of();
    }



}
