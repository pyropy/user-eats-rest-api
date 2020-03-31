package com.pyropy.usereats.dto;

import lombok.Getter;
import lombok.Setter;

// todo: attachment
@Getter
@Setter
public class NotificationDto {
    private String subject;
    private String content;

    public NotificationDto(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }
}
