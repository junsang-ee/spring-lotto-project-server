package com.lotto.web.repository.criteria;

import com.lotto.web.config.dsl.Criteria;
import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.model.entity.QPostEntity;

import lombok.Setter;
import lombok.experimental.Accessors;


@Setter
@Accessors(chain = true, fluent = true)
public class PostCriteria extends Criteria {

    private static final QPostEntity ROOT = QPostEntity.postEntity;

    private BoardEntity parentBoard;

    private PostActivationStatus status;

    @Override
    protected void build(PredicateBuilder predicates) {
        if (status != null) {
            predicates.and(eq(ROOT.status, status));
        }
        predicates.and(eq(ROOT.parentBoard, parentBoard));


        predicates.build();
    }
}
