package com.pyropy.usereats.model;

// todo: attachment
public class Notification {
    private String subject;
    private String content;

    public Notification(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
