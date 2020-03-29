package com.pyropy.usereats.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "application.sendgrid")
public class SendGridConfig {
    private String sendGridAPIKey;
    private String replyEmail;

    public SendGridConfig() {
    }

    public String getReplyEmail() {
        return replyEmail;
    }

    public String getSendGridAPIKey() {
        return sendGridAPIKey;
    }
}

