package com.reddot.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@SecurityScheme(
    name = "REDDOT API",
    type = SecuritySchemeType.HTTP,
    scheme = "basic",
    `in` = SecuritySchemeIn.HEADER
)
class OpenApiConfig {

    @Bean
    fun reddotOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info().title("REDDOT API")
                .description("Backend API for REDDOT Application")
                .version("v1.0.0")
                .license(License()
                    .name("Apache 2.0")
                    .url("https://github.com/gusrylmubarok/reddot/LICENSE.md")))
            .externalDocs(
                ExternalDocumentation()
                .description("REDDOT Wiki Documentation")
                .url("https://github.com/gusrylmubarok/reddot/LICENSE.md"))
    }

}