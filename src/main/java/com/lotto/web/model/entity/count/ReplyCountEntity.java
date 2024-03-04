package com.lotto.web.model.entity.count;

import com.lotto.web.constants.UserStatus;
import com.lotto.web.model.SequentialEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "reply_count")
@Table(name = "reply_count")
public class ReplyCountEntity extends SequentialEntity {

    private int enabledCount;

    private int disabledCount;


    @PrePersist
    private void onPrevisionPersist() {
        this.enabledCount = 0;
        this.disabledCount = 0;
    }
}
