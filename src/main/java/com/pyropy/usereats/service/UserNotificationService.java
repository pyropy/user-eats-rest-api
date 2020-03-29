package com.pyropy.usereats.service;

import com.pyropy.usereats.model.Notification;
import com.pyropy.usereats.model.User;

public interface UserNotificationService {
    public void notifyUser(User from, User to, Notification notification);
}
