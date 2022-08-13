package com.gusrylmubarok.reddit.backend.service;

import com.gusrylmubarok.reddit.backend.model.NotificationEmail;
import org.springframework.scheduling.annotation.Async;

public interface MailService {
    void sendMail(NotificationEmail notificationEmail);
}
