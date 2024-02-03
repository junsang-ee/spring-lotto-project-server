package com.lotto.web.model.entity.lotto;

import com.lotto.web.model.entity.CreationTimestampEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "lottoHistory")
@Entity(name = "lottoHistory")
public class LottoWinningHistoryEntity extends CreationTimestampEntity {
    private int firstNumber;
    private int secondNumber;
    private int thirdNumber;
    private int fourthNumber;
    private int fifthNumber;
    private int sixthNumber;
    private int bonusNumber;
    private int round;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date drawDate;
}