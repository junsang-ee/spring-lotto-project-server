package com.lotto.web.model.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MailTemplate {
    VERIFY_EMAIL("JUNSANG LOTTO 이메일 주소 인증",
            "인증 코드를 입력하여 이메일 주소를 인증해 주세요.\n이 인증 코드는 발급 후 3분 동안만 유효합니다."),
    RESET_PASSWORD("JUNSANG LOTTO 비밀번호 변경 안내",
            "임시 비밀번호를 발급하였습니다.\n임시 발급 비밀번호로 로그인 하시기 바랍니다.");

    private final String subject;
    private final String notice;

}
