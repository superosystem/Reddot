package com.reddot.service.mailer

import com.reddot.data.vo.NotificationEmail
import com.reddot.exception.MailReddotException
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class MailServiceImpl(
    val mailSender: JavaMailSender,
    val mailContentBuilder: MailContentBuilder
) {

    @Async
    fun sendMail(notificationEmail: NotificationEmail) {
        val message = MimeMessagePreparator {
            mimeMessage ->
            run {
                val messageHelper = MimeMessageHelper(mimeMessage)
                messageHelper.setFrom("info@reddot.com")
                messageHelper.setTo(notificationEmail.recipient)
                messageHelper.setSubject(notificationEmail.subject)
                messageHelper.setText(mailContentBuilder.build(notificationEmail.body))
            }
        }
        try {
            mailSender.send(message)
        }catch (ex: MailException) {
            throw MailReddotException("Failed to send email activation account")
        }
    }

}