package com.baeldung.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 */
@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/index")
    public String index() {
        return "Hello";
    }

    @GetMapping("/testAuth")
    public String testAuth() {
        return "Auth Success";
    }
}
