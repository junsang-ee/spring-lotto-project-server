package com.lotto.web.model.entity.count;

import com.lotto.web.constants.countable.CountableType;
import com.lotto.web.model.SequentialEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Entity(name = "post_count")
@Table(name = "post_count")
public class PostCountEntity extends SequentialEntity {

    private int enabledCount;

    private int disabledCount;

    public static PostCountEntity of() {
        return new PostCountEntity(0, 0);
    }

    public void updateCount(CountableType type) {
        switch (type) {
            case CREATE:
                this.enabledCount++; break;
            case REMOVE:
            case DISABLE:
            case PURGE:
                if (this.enabledCount > 0) this.enabledCount--;
                if (type != CountableType.PURGE)
                    this.disabledCount++;
                break;
            case RESTORE:
                this.disabledCount--;
                this.enabledCount++;

        }
    }

}
