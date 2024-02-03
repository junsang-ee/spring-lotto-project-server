package com.lotto.web.model.entity.lotto;

import com.lotto.web.model.TimestampSequentialEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

    @OneToOne
    @JoinColumn(name = "winning_status")
    private WinningStatusEntity winningStatus;
}
