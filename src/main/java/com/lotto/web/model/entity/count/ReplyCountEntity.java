package com.lotto.web.model.entity.count;

import com.lotto.web.model.SequentialEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

import static lombok.AccessLevel.PROTECTED;
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Entity(name = "reply_count")
@Table(name = "reply_count")
public class ReplyCountEntity extends SequentialEntity {

    private int enabledCount;

    private int disabledCount;

    public static ReplyCountEntity of() {
        return new ReplyCountEntity(
                0,
                0
        );
    }

    public void addEnabled() {
        this.enabledCount++;
    }

    public void addDisabled() {
        if (this.enabledCount > 0)
            this.enabledCount--;
        this.disabledCount++;
    }

    public void cancel() {
        if (this.disabledCount > 0)
            this.disabledCount--;
        this.enabledCount++;
    }

}
