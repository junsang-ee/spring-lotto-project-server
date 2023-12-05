package com.lotto.web.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class MessageUtil {
    @Resource
    private MessageSource source;

    static MessageSource messageSource;

    @PostConstruct
    public void initialize() {
        messageSource = source;
    }

    public static String getMessage(String message) {
        return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
    }
}
