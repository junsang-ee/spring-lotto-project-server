package com.lotto.web.model.entity;

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
@Entity
@Table(name = "lottoHistory")
public class LottoHistoryEntity extends CreationTimestampEntity{
    private int firstNumber;
    private int secondNumber;
    private int thirdNumber;
    private int fourthNumber;
    private int fifthNumber;
    private int sixthNumber;
    private long round;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date drawDate;
}
