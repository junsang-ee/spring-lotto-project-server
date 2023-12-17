package com.lotto.web.model.entity;

import com.lotto.web.constants.PostDisclosureType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "post")
public class PostEntity extends AbstractPostEntity {
    private String title;

    private String content;

    private PostDisclosureType disclosureType;

    private String password;

}
