package com.gusrylmubarok.reddit.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Map;

@SpringBootApplication
@EnableAsync
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(com.gusrylmubarok.reddit.backend.BackendApplication.class, args);
	}

}
