package com.reddot.mail

import com.reddot.exception.BadRequestException
import com.reddot.model.EmailNotify
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class MailServiceImpl(
    private val mailSender: JavaMailSender,
) {
    @Async
    fun sendMail(emailNotify: EmailNotify) {
        val message = MimeMessagePreparator {
                mimeMessage ->
            run {
                val messageHelper = MimeMessageHelper(mimeMessage)
                messageHelper.setFrom("info@reddot.com")
                messageHelper.setTo(emailNotify.recipient)
                messageHelper.setSubject(emailNotify.subject)
                messageHelper.setText(emailNotify.body)
            }
        }
        try {
            mailSender.send(message)
        }catch (ex: Exception) {
            throw BadRequestException("failed to send activation account")
        }
    }
}