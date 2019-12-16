package com.github.dragonetail.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;
import java.security.Principal;

@FeignClient("${service.B}")
public interface HelloService {

    @GetMapping("service-b/hello")
    String hello();
//    String hello(@RequestParam("str") String param);


    @GetMapping("service-b/secret")
    String helloSecret();
}