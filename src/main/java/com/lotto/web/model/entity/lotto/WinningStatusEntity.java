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

    @OneToOne(mappedBy = "winningStatus")
    private ExtractionHistoryEntity extraction;
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

    public void updateStatus(int element, MatchStatus matchStatus) {
        if (element == this.extraction.getFirstNumber())
            this.firstStatus = matchStatus;
        else if (element == this.extraction.getSecondNumber())
            this.secondStatus = matchStatus;
        else if (element == this.extraction.getThirdNumber())
            this.thirdStatus = matchStatus;
        else if (element == this.extraction.getFourthNumber())
            this.fourthStatus = matchStatus;
        else if (element == this.extraction.getFifthNumber())
            this.fifthStatus = matchStatus;
        else if (element == this.extraction.getSixthNumber())
            this.sixthStatus = matchStatus;
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
