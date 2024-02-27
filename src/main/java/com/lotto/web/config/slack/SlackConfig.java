package com.lotto.web.config.slack;

import ch.qos.logback.classic.Level;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@ConfigurationProperties(prefix = "integration")
@Component
public class SlackConfig {

    private Level level;
    private Slack slack;

    @Data
    public static class Slack{
        private boolean enabled;
        private String webHookUrl;
        private String channel;
    }
}
