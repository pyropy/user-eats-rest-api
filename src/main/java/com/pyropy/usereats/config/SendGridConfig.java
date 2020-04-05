package com.pyropy.usereats.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "application.sendgrid")
public class SendGridConfig {
    private String apiKey;
    private String replyEmail;
    private String defaultSenderEmail;

    public SendGridConfig() {
    }

    public String getReplyEmail() {
        return replyEmail;
    }

    public String getDefaultSenderEmail() {
        return defaultSenderEmail;
    }

    public void setReplyEmail(String replyEmail) {
        this.replyEmail = replyEmail;
    }

    public void setDefaultSenderEmail(String defaultSenderEmail) {
        this.defaultSenderEmail = defaultSenderEmail;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}

