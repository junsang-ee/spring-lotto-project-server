package com.lotto.web.config.slack;

import ch.qos.logback.classic.LoggerContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SlackContextConfig implements InitializingBean {
    private final SlackConfig slackConfig;

    @Override
    public void afterPropertiesSet() throws Exception {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        SlackAppender slackAppender = new SlackAppender(slackConfig);
        slackAppender.setContext(loggerContext);
        slackAppender.setName("SlackAppender");
        slackAppender.start();
        loggerContext.getLogger("ROOT").addAppender(slackAppender);
    }
}
