package com.lotto.web.config.slack;

import lombok.Data;
import com.lotto.web.util.Constants;
import java.time.LocalDateTime;

@Data
public class SlackLog {
    private String loggerName;
    private String serverName;
    private String hostName;
    private String message;
    private String trace;
    private LocalDateTime errorDateTime = LocalDateTime.now(Constants.ZONE_KR);
}
