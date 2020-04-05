package com.pyropy.usereats.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SendGridClient {

    @Autowired
    private SendGridConfig sendGridConfig;

    @Bean
    public SendGrid getSendGrid() {
        return new SendGrid(sendGridConfig.getApiKey());
    }
}
