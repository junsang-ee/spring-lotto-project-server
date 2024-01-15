package com.lotto.web.service.mail;

import com.lotto.web.constants.mail.MailConstants;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.InvalidStateException;
import com.lotto.web.model.dto.mail.MailTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendMail(String email, MailTemplate template, String authCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    true,
                    StandardCharsets.UTF_8.name()
            );
            String content = loadHtmlContent();
            if (content == null) {
                content = "<div>";
                content +=  "<div style='text-align=center;'><h1>" + template.getSubject() + "</h1></div>";
                content +=  "<div style='text-align=center;'><h1> 이메일 인증 코드 : " + authCode + "</h1></div>";
                content +="</div>";
            } else {
                content = content.replace("${subject}", template.getSubject());
                content = content.replace("${authCode}", authCode);
            }
            helper.setFrom(MailConstants.SENDER);
            helper.setTo(email);
            helper.setSubject(MailTemplate.VERIFY_EMAIL.getSubject());
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new InvalidStateException(ErrorMessage.AUTH_SEND_EMAIL);
        }

    }

    private void setEmailForm(SimpleMailMessage message,
                              String toEmail,
                              String title,
                              String content) {
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(content);
    }

    private String loadHtmlContent() {
        try {
            Resource resource = new ClassPathResource("templates/email-template.html");
            Path path = Paths.get(resource.getURI());
            return Files.readString(path);
        } catch (IOException e) {
            return null;
        }
    }
}
