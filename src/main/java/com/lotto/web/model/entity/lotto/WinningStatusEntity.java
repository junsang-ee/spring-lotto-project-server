package com.lotto.web.model.entity.lotto;

import com.lotto.web.constants.WinningStatus;
import com.lotto.web.model.TimestampSequentialEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Table
@Entity(name = "winning_status")
public class WinningStatusEntity extends TimestampSequentialEntity {

    @Enumerated(EnumType.STRING)
    private WinningStatus firstStatus;

    @Enumerated(EnumType.STRING)
    private WinningStatus secondStatus;

    @Enumerated(EnumType.STRING)
    private WinningStatus thirdStatus;

    @Enumerated(EnumType.STRING)
    private WinningStatus fourthStatus;

    @Enumerated(EnumType.STRING)
    private WinningStatus fifthStatus;

    @Enumerated(EnumType.STRING)
    private WinningStatus sixthStatus;

    @Enumerated(EnumType.STRING)
    private WinningStatus overallStatus;

    public static WinningStatusEntity of() {
        return new WinningStatusEntity();
    }

    private WinningStatusEntity() {
        this.firstStatus = WinningStatus.PENDING;
        this.secondStatus = WinningStatus.PENDING;
        this.thirdStatus = WinningStatus.PENDING;
        this.fourthStatus = WinningStatus.PENDING;
        this.fifthStatus = WinningStatus.PENDING;
        this.sixthStatus = WinningStatus.PENDING;
        this.overallStatus = WinningStatus.PENDING;
    }
}
