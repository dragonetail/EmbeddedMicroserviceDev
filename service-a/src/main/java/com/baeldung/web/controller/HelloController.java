package com.baeldung.web.controller;

import com.baeldung.web.dto.Bar;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@RestController
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public String findById() {
        return "Hello world!";
    }

}
