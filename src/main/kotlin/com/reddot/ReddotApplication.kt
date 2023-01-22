package com.reddot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class ReddotApplication

fun main(args: Array<String>) {
	runApplication<ReddotApplication>(*args)
}
