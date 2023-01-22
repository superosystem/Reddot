package com.reddot.service.mailer

import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class MailContentBuilder (
    val templateEngine: TemplateEngine
) {

    fun build(message: String): String {
        val context: Context = Context()
        context.setVariable("message", message)

        return templateEngine.process("mailTemplate", context)
    }

}