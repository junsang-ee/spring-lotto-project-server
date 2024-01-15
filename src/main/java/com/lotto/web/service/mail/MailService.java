package com.lotto.web.service.mail;

public interface MailService {

    void sendMail(String email, String title, String detail);
}
