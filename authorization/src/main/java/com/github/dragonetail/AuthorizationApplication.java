package com.github.dragonetail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 认证服务启动器
 *
 * @author sunyx
 */
@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
public class AuthorizationApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApplication.class, args);
    }

}