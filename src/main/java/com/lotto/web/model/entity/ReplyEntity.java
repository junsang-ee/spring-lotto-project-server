package com.lotto.web.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "reply")
@Entity(name = "reply")
public class ReplyEntity extends AbstractPostEntity {
}
