package com.lotto.web.model.entity.lotto;

import com.lotto.web.model.TimestampSequentialEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
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
}
