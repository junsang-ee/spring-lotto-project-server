package com.lotto.web.repository;

import com.lotto.web.model.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, String> {
}
