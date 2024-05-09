package com.lotto.web.model.entity.lotto;

import com.lotto.web.model.TimestampSequentialEntity;
import com.lotto.web.model.dto.api.LottoApiResponse;

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

    public static LottoWinningHistoryEntity of(final LottoApiResponse response) {
        return new LottoWinningHistoryEntity(
                response.getDrwtNo1(), response.getDrwtNo2(),
                response.getDrwtNo3(), response.getDrwtNo4(),
                response.getDrwtNo5(), response.getDrwtNo6(),
                response.getBnusNo(), response.getDrwNo(),
                response.getDrwNoDate()
        );
    }

}
