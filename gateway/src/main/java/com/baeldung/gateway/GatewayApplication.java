package com.baeldung.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {// @formatter:off
        return builder.routes()
                .route(r -> r
                        .path("/service-a/**")
                        //.filters(f -> f.stripPrefix(1))
                        .uri("lb://service-a/")
                )
                .route(r -> r
                        .path("/uaa/**")
                        //.filters(f -> f.stripPrefix(1))
                        .uri("lb://authorization/")
                ).build();
    }// @formatter:on

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
