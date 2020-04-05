package com.pyropy.usereats.service;

import com.pyropy.usereats.config.SendGridConfig;
import com.pyropy.usereats.dto.NotificationDto;
import com.pyropy.usereats.model.FoodArticle;
import com.pyropy.usereats.model.FoodArticleOrder;
import com.pyropy.usereats.model.Order;
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
import java.util.*;
import java.util.stream.Collectors;

@Service("sendGrid")  // todo: Make notifications async
public class SendGridEmailService implements UserNotificationService {

    @Autowired
    private SendGridConfig sendGridConfig;

    @Autowired
    private SendGrid sendGridClient;

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
    public void notifyUser(User from, User to, NotificationDto notificationDto) {
        Content content = new Content("text/html", notificationDto.getContent());
        sendEmail(from.getEmail(), to.getEmail(), notificationDto.getSubject(), content);
    }

    /* Notifies restaurant owners upon user order confirmation */
    public void notifyRestaurantOwnersConfirmedOrder(Order order) {
        User notificationUser = getDefaultNotificationUser();
        createOwnerOrdersMap(order).forEach((owner, foodArticles) -> {
            NotificationDto notificationDto = generateRestaurantOwnerOrderEmail(foodArticles, order.getDeliveryAddress());
            notifyUser(notificationUser, owner, notificationDto);
        });
    }

    private User getDefaultNotificationUser() {
        // todo: replace this with more elegant solution
        User notificationUser = new User();
        notificationUser.setEmail(sendGridConfig.getDefaultSenderEmail()); // todo: load email from config
        return notificationUser;
    }

    private NotificationDto generateRestaurantOwnerOrderEmail(List<FoodArticleOrder> foodArticles, String deliveryAdders) {
        // todo: load html template
        String notificationContent = foodArticles
                .stream()
                .map(foodArticleOrder -> new StringBuilder()
                        .append(foodArticleOrder.getFoodArticle().getName())
                        .append(" - ").append(foodArticleOrder.getQuantity().toString())
                        .toString())
                .collect(Collectors.joining());
        return new NotificationDto("New Order", String.format("%s\n%s", notificationContent, deliveryAdders));
    }

    /* Creates HashMap with list of FoodArticleOrders for each restaurant owner */
    private HashMap<User, List<FoodArticleOrder>> createOwnerOrdersMap(Order order) {
        HashMap<User, List<FoodArticleOrder>> ownerOrders = new HashMap<>();

        order.getFoodArticles()
                .forEach(foodArticleOrder -> {
                    FoodArticle foodArticle = foodArticleOrder.getFoodArticle();
                    User owner = foodArticle.getRestaurant().getOwner();
                    if (ownerOrders.containsKey(owner))
                        ownerOrders.get(owner).add(foodArticleOrder);
                    else ownerOrders.put(owner, Collections.singletonList(foodArticleOrder));
                });

        return ownerOrders;
    }


}
