package com.lotto.web.config.slack;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.util.ContextUtil;
import lombok.RequiredArgsConstructor;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackAttachment;
import net.gpedro.integrations.slack.SlackField;
import net.gpedro.integrations.slack.SlackMessage;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class SlackAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private final SlackConfig slackConfig;
    @Override
    protected void append(ILoggingEvent loggingEvent) {
        if(loggingEvent.getLevel().isGreaterOrEqual(slackConfig.getLevel())){
            SlackLog errorLog = getErrorLog(loggingEvent);

            if(slackConfig.getSlack().isEnabled()){
                toSlack(errorLog);
            }
        }
    }

    private void toSlack(SlackLog errorLog){
        try {
            SlackApi slackApi = new SlackApi(slackConfig.getSlack().getWebHookUrl());

            List<SlackField> fields = new ArrayList<>();

            SlackField message = new SlackField();
            message.setTitle("Error");
            message.setValue(errorLog.getMessage());
            message.setShorten(false);
            fields.add(message);

            SlackField date = new SlackField();
            date.setTitle("OccurredAt");
            date.setValue(errorLog.getErrorDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            date.setShorten(true);
            fields.add(date);

            SlackField serverName = new SlackField();
            serverName.setTitle("Server Name");
            serverName.setValue(errorLog.getServerName());
            serverName.setShorten(true);
            fields.add(serverName);

            SlackField hostName = new SlackField();
            hostName.setTitle("Host Name");
            hostName.setValue(errorLog.getHostName());
            hostName.setShorten(false);
            fields.add(hostName);

            String title = errorLog.getMessage();

            SlackAttachment slackAttachment = new SlackAttachment();
            slackAttachment.setFallback("Error!!");
            slackAttachment.setColor("danger");
            slackAttachment.setFields(fields);
            slackAttachment.setTitle(title);
            slackAttachment.setText(errorLog.getTrace());

            SlackMessage slackMessage = new SlackMessage("");
            slackMessage.setChannel("#" + slackConfig.getSlack().getChannel());
            slackMessage.setUsername(String.format("[%s] - ErrorReport", "ErrorMan"));
            slackMessage.setIcon(":exclamation:");
            slackMessage.setAttachments(Collections.singletonList(slackAttachment));

            slackApi.call(slackMessage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public SlackLog getErrorLog(ILoggingEvent loggingEvent){
        if(loggingEvent.getLevel().isGreaterOrEqual(slackConfig.getLevel())){
            SlackLog errorLog = new SlackLog();
            errorLog.setLoggerName(loggingEvent.getLoggerName());
            errorLog.setServerName("junsang-lotto");
            errorLog.setHostName(getHostName());
            errorLog.setMessage(loggingEvent.getFormattedMessage());

            if(loggingEvent.getThrowableProxy() != null){
                errorLog.setTrace(getStackTrace(loggingEvent.getThrowableProxy().getStackTraceElementProxyArray()));
            }

            return errorLog;
        }

        return null;
    }

    private String getHostName() {
        try{
            return ContextUtil.getLocalHostName();
        }catch(SocketException | UnknownHostException ignored){
        }
        return null;
    }

    private String getStackTrace(StackTraceElementProxy[] stackTraceElements){
        if(stackTraceElements == null || stackTraceElements.length == 0){
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for(StackTraceElementProxy element : stackTraceElements){
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

}