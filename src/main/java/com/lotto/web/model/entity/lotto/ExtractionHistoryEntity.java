package com.lotto.web.model.entity.lotto;

import com.lotto.web.model.TimestampSequentialEntity;
import com.lotto.web.model.dto.response.DefaultLottoResponse;
import com.lotto.web.model.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "ExtractionHistory")
@Entity(name = "ExtractionHistory")
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

    public ExtractionHistoryEntity(UserEntity user, DefaultLottoResponse lotto) {
        this.firstNumber = lotto.getFirstNumber();
        this.secondNumber = lotto.getSecondNumber();
        this.thirdNumber = lotto.getThirdNumber();
        this.fourthNumber = lotto.getFourthNumber();
        this.fifthNumber = lotto.getFifthNumber();
        this.sixthNumber = lotto.getSixthNumber();
        this.createdBy = user;
        this.winningStatus = new WinningStatusEntity();
    }

}
