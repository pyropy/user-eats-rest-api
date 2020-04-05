package com.pyropy.usereats.service;

import com.pyropy.usereats.dto.NotificationDto;
import com.pyropy.usereats.model.User;

public interface UserNotificationService {
    void notifyUser(User from, User to, NotificationDto notificationDto);
}
