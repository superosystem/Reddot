package com.reddot.mail

import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class MailContentService(
    private val templateEngine: TemplateEngine
) {
    fun build(message: String) : String {
        val context = Context()
        context.setVariable("message", message)

        return templateEngine.process("mailTemplate", context)
    }
}