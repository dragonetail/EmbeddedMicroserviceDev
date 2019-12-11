package com.github.dragonetail.controller;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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


    @GetMapping({ "/user", "/me" })
    @ResponseBody
    public String user(Principal principal) {

//        public String user(@AuthenticationPrincipal UserDetails userDetails) {
        return principal.getName();
    }
}
