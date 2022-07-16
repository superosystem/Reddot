package com.gusrylmubarok.reddit.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloWorld {
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World";
    }
}
