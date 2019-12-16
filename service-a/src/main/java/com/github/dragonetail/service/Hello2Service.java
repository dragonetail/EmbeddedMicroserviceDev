package com.github.dragonetail.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@FeignClient("${service.B}")
public interface Hello2Service {

    @GetMapping("service-b/secret")
    String helloSecret(URI baseUrl);
}