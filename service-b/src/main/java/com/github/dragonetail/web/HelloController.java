package com.github.dragonetail.web;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HelloController {

    @GetMapping(value = "/hello")
    @ResponseBody
    public String helloWorld(Principal principal) {
        return principal == null ? "Hello anonymous" : "Hello " + principal.getName();
    }

    @PreAuthorize("#oauth2.hasScope('web') and hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/secret")
    @ResponseBody
    public String helloSecret(Principal principal) {
        return principal == null ? "Hello anonymous" : "S3CR3T  - Hello " + principal.getName();
    }
}