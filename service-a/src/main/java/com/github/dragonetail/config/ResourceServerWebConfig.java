package com.github.dragonetail.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// @EnableWebMvc
@ComponentScan({ "com.github.dragonetail.web.controller" })
public class ResourceServerWebConfig implements WebMvcConfigurer {
    //
}
