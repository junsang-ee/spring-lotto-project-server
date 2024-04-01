package com.lotto.web.model.entity.lotto;

import com.lotto.web.model.TimestampSequentialEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
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

    public static LottoWinningHistoryEntity of(final List<Integer> winningList,
                                               final int bonus,
                                               final int round,
                                               final Date drawDate) {
        return new LottoWinningHistoryEntity(
                winningList,
                bonus,
                round,
                drawDate
        );
    }

    public LottoWinningHistoryEntity(List<Integer> winningList,
                                     int bonus,
                                     int round,
                                     Date drawDate) {
        this.firstNumber = winningList.get(0);
        this.secondNumber = winningList.get(1);
        this.thirdNumber = winningList.get(2);
        this.fourthNumber = winningList.get(3);
        this.fifthNumber = winningList.get(4);
        this.sixthNumber = winningList.get(5);
        this.bonusNumber = bonus;
        this.round = round;
        this.drawDate = drawDate;

    }
}
