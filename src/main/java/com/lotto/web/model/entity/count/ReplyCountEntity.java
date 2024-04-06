package com.lotto.web.model.entity.count;

import com.lotto.web.constants.countable.CountableType;
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

    public void updateCount(CountableType type) {
        switch (type) {
            case CREATE:
                this.enabledCount++; break;
            case REMOVE:
            case DISABLE:
                if (this.enabledCount > 0) this.enabledCount--;
                this.disabledCount++; break;
            case RESTORE:
                this.disabledCount--;
                this.enabledCount++;
        }
    }

}
