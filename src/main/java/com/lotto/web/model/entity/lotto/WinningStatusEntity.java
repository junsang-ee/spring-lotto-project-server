package com.lotto.web.model.entity.lotto;

import com.lotto.web.constants.MatchStatus;
import com.lotto.web.constants.WinningResultType;
import com.lotto.web.model.TimestampSequentialEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Table
@Entity(name = "winning_status")
public class WinningStatusEntity extends TimestampSequentialEntity {

    @Enumerated(EnumType.STRING)
    private MatchStatus firstStatus;

    @Enumerated(EnumType.STRING)
    private MatchStatus secondStatus;

    @Enumerated(EnumType.STRING)
    private MatchStatus thirdStatus;

    @Enumerated(EnumType.STRING)
    private MatchStatus fourthStatus;

    @Enumerated(EnumType.STRING)
    private MatchStatus fifthStatus;

    @Enumerated(EnumType.STRING)
    private MatchStatus sixthStatus;

    @Enumerated(EnumType.STRING)
    private WinningResultType winningResult;

    public static WinningStatusEntity of() {
        return new WinningStatusEntity();
    }

    private WinningStatusEntity() {
        this.firstStatus = MatchStatus.PENDING;
        this.secondStatus = MatchStatus.PENDING;
        this.thirdStatus = MatchStatus.PENDING;
        this.fourthStatus = MatchStatus.PENDING;
        this.fifthStatus = MatchStatus.PENDING;
        this.sixthStatus = MatchStatus.PENDING;
        this.winningResult = WinningResultType.PENDING;
    }

    public void updateToWaiting() {
        this.firstStatus = MatchStatus.WAITING;
        this.secondStatus = MatchStatus.WAITING;
        this.thirdStatus = MatchStatus.WAITING;
        this.fourthStatus = MatchStatus.WAITING;
        this.fifthStatus = MatchStatus.WAITING;
        this.sixthStatus = MatchStatus.WAITING;
        this.winningResult = WinningResultType.WAITING;
    }

    public boolean getIsWinning() {
        return this.getWinningResult() != WinningResultType.LOST &&
                this.getWinningResult() != WinningResultType.PENDING &&
                this.getWinningResult() != WinningResultType.WAITING;
    }
}
