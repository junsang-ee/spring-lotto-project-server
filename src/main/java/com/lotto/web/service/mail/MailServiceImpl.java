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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendMail(String email, MailTemplate template, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    true,
                    StandardCharsets.UTF_8.name()
            );
            String content = loadHtmlContent();
            if (content != null) {
                content = content.replace("${subject}", template.getSubject());
                content = content.replace("${titleSubject}",
                        template == MailTemplate.VERIFY_EMAIL ? "인증 코드" : "임시 비밀번호");
                content = content.replace("${tempCode}", code);
                content = content.replace("${notice}", template.getNotice());
            } else {
                if (template == MailTemplate.VERIFY_EMAIL)
                    content = "<div> 인증 코드 : " + code + "</div>";
                else content = "<div> 임시 비밀번호 : " + code + "</div>";
            }

            helper.setFrom(MailConstants.SENDER);
            helper.setTo(email);
            helper.setSubject(template.getSubject());
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
            InputStreamReader inputStreamReader = new InputStreamReader(
                    resource.getInputStream(),
                    StandardCharsets.UTF_8
            );
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String html = "";
            StringBuilder result = new StringBuilder();
            while ((html = bufferedReader.readLine()) != null) {
                result.append(html);
            }
            return result.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
