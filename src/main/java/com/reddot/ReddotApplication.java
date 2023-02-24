package com.reddot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ReddotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReddotApplication.class, args);
	}

}
