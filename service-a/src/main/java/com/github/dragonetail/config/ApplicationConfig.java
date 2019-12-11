package com.github.dragonetail.config;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.github.dragonetail"})
public class ApplicationConfig {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    RequestInterceptor oauth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext) {
//        return  new OAuth2FeignRequestInterceptor(oAuth2ClientContext, new ResourceOwnerPasswordResourceDetails());
//    }
//
////        @Bean
////    public OAuth2ClientContext oauth2ClientContext() {
////        return new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest());
////    }
//
//    @Bean
//    Logger.Level feignLoggerLevel() {
//       return  Logger.Level.BASIC;
//    }

//    @Bean
//    protected RequestInterceptor oauth2FeignRequestInterceptor(
//            OAuth2ClientContext context,
//            OAuth2ProtectedResourceDetails resourceDetails) {
//
//        return new OAuth2FeignRequestInterceptor(context, resourceDetails);
//    }
//
//
//    @Bean
//    protected OAuth2ProtectedResourceDetails resource() {
//
//        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
//        resource.setAccessTokenUri("http://localhost:8081/uaa/oauth/token");
//
//        resource.setClientAuthenticationScheme(AuthenticationScheme.header);
//        resource.setClientId("client-a");
//        resource.setClientSecret("client-password");
//
//
//        resource.setGrantType("password");
//        resource.setScope(Arrays.asList("web"));
//
//        resource.setUsername("admin");
//        resource.setPassword("password");
//
//        return resource;
//    }
//
//    @Bean
//    public OAuth2ClientContext oauth2ClientContext() {
//        return new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest());
//    }
//
//    @Bean
//    @Primary
//    public OAuth2RestTemplate oauth2RestTemplate(
//           OAuth2ClientContext context,
//           OAuth2ProtectedResourceDetails resourceDetails) {
//
//        OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails,
//                context);
//
//        return template;
//    }

}
