package com.gusrylmubarok.reddit.backend;

import com.gusrylmubarok.reddit.backend.configuration.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(com.gusrylmubarok.reddit.backend.BackendApplication.class, args);
	}

}
