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

    public void updateStatus(int numberOrder, MatchStatus matchStatus) {
        switch (numberOrder) {
            case 0: this.firstStatus = matchStatus; break;
            case 1: this.secondStatus = matchStatus; break;
            case 2: this.thirdStatus = matchStatus; break;
            case 3: this.fourthStatus = matchStatus; break;
            case 4: this.fifthStatus = matchStatus; break;
            case 5: this.sixthStatus = matchStatus; break;
        }
    }

    public void updateWinningResult(int matchCount, boolean isMatchBonus) {
        switch (matchCount) {
            case 0:
            case 1:
            case 2:
                this.winningResult = WinningResultType.LOST; break;
            case 3:
                this.winningResult = WinningResultType.FIFTH_PLACE; break;
            case 4:
                this.winningResult = WinningResultType.FOURTH_PLACE; break;
            case 5:
                if (isMatchBonus) this.winningResult = WinningResultType.SECOND_PLACE;
                else this.winningResult = WinningResultType.THIRD_PLACE;
                break;
            case 6: this.winningResult = WinningResultType.FIRST_PLACE;
        }

    }


}
