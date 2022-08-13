package com.gusrylmubarok.reddit.backend.service.impl;

import com.gusrylmubarok.reddit.backend.exceptions.BackendRedditException;
import com.gusrylmubarok.reddit.backend.mail.MailContentBuilder;
import com.gusrylmubarok.reddit.backend.model.NotificationEmail;
import com.gusrylmubarok.reddit.backend.service.MailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Override
    @Async
    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springreddit@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new BackendRedditException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
        }
    }
}
