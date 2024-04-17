package com.lotto.web.model.entity.lotto;

import com.lotto.web.model.TimestampSequentialEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Table(name = "lotto_winning_history")
@Entity(name = "lotto_winning_history")
public class LottoWinningHistoryEntity extends TimestampSequentialEntity {
    private int firstNumber;
    private int secondNumber;
    private int thirdNumber;
    private int fourthNumber;
    private int fifthNumber;
    private int sixthNumber;
    private int bonusNumber;
    private int round;

    @Temporal(TemporalType.DATE)
    private Date drawDate;

    public static LottoWinningHistoryEntity of(final int firstNumber, final int secondNumber,
                                               final int thirdNumber, final int fourthNumber,
                                               final int fifthNumber, final int sixthNumber,
                                               final int bonusNumber, final int round,
                                               final Date drawDate) {
        return new LottoWinningHistoryEntity(
                firstNumber, secondNumber, thirdNumber,
                fourthNumber, fifthNumber, sixthNumber,
                bonusNumber, round, drawDate
        );
    }

}
