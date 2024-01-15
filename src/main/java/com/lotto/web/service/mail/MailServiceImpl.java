package com.lotto.web.service.mail;

import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.InvalidStateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendMail(String email, String title, String detail) {
        SimpleMailMessage message = new SimpleMailMessage();
        setEmailForm(message, email, title, detail);
        try {
            mailSender.send(message);
        } catch (RuntimeException e) {
            log.debug("MailService.sendEmail exception occur toEmail: {}, " +
                    "title: {}, text: {}", email, title, detail);
            throw new InvalidStateException(ErrorMessage.REPLY_NOT_FOUND);
        }

    }

    private void setEmailForm(SimpleMailMessage message, String toEmail, String title, String detail) {
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(detail);
    }
}
