package com.reddot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReddotApplication

fun main(args: Array<String>) {
	runApplication<ReddotApplication>(*args)
}
