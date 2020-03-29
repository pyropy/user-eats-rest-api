package com.pyropy.usereats.service;

import com.pyropy.usereats.config.SendGridConfig;
import com.pyropy.usereats.model.Notification;
import com.pyropy.usereats.model.User;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("sendGrid")
public class SendGridEmailService implements UserNotificationService {

    @Autowired
    private SendGrid sendGridClient;

    @Autowired
    private SendGridConfig sendGridConfig;

    private Response sendEmail(String from, String to, String subject, Content content) {
        Mail mail = new Mail(new Email(from), subject, new Email(to), content);
        mail.setReplyTo(new Email(from));
        Request request = new Request();
        Response response = null;
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            this.sendGridClient.api(request);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }

    @Override
    public void notifyUser(User from, User to, Notification notification) {
        Content content = new Content("text/html", notification.getContent());
        sendEmail(from.getEmail(), to.getEmail(), notification.getSubject(), content);
    }
}
