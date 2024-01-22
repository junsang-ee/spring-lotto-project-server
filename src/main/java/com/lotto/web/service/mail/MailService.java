package com.lotto.web.service.mail;

import com.lotto.web.model.dto.mail.MailTemplate;

public interface MailService {

    void sendMail(String email, MailTemplate template, String code);
}
