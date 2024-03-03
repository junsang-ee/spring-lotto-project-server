package com.lotto.web.model.entity.count;

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
@Entity(name = "post_count")
@Table(name = "post_count")
public class PostCountEntity extends SequentialEntity {

    private int enabledCount;

    private int disabledCount;

    private int removedCount;

    @PrePersist
    private void onPrevisionPersist() {
        this.enabledCount = 0;
        this.disabledCount = 0;
        this.removedCount = 0;
    }
}
