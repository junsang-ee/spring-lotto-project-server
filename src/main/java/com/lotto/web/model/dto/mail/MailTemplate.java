package com.lotto.web.model.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MailTemplate {
    VERIFY_EMAIL("JUNSANG_LOTTO 이메일 주소 인증"),
    RESET_PASSWORD("JUNSANG_LOTTO 비밀번호 변경 안내");

    private final String subject;

}
