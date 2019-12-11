package com.github.dragonetail.web.controller;

import com.github.dragonetail.service.AuthPocService;
import com.github.dragonetail.service.HelloService;
import com.github.dragonetail.web.dto.AuthPocResultDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.Map;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@RestController
public class AuthPocController {

    private AuthPocService authPocService;
    private TokenStore tokenStore;
    private HelloService helloService;

    public AuthPocController(AuthPocService authPocService, TokenStore tokenStore,
                             HelloService helloService) {
        this.authPocService = authPocService;
        this.tokenStore = tokenStore;
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello world!";
    }


    @GetMapping(value = "/byIdWithServiceAdmin/{id}")
    @ResponseBody
    public AuthPocResultDTO byIdWithServiceAdmin(@PathVariable final long id) {
        return authPocService.byId(id);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/byIdWithAdmin/{id}")
    @ResponseBody
    public AuthPocResultDTO byIdWithAdmin(@PathVariable final long id) {
        return new AuthPocResultDTO(id, randomAlphabetic(4));
    }

    @PreAuthorize("#oauth2.hasScope('web') or #oauth2.hasScope('mobile') and hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/byIdWithAdminAndScope/{id}")
    @ResponseBody
    public AuthPocResultDTO byIdWithAdminAndScope(@PathVariable final long id) {
        return new AuthPocResultDTO(id, randomAlphabetic(4));
    }


    @PreAuthorize("#oauth2.hasScope('web')")
    @GetMapping(value = "/users/extra")
    @ResponseBody
    public Map<String, Object> getExtraInfo(OAuth2Authentication auth) {
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        final OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
        System.out.println(accessToken);
        return accessToken.getAdditionalInformation();
    }


    @PreAuthorize("#oauth2.hasScope('web')")
    @GetMapping(value = "/users/principal")
    @ResponseBody
    public Authentication getPrincipal(@AuthenticationPrincipal String user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication;
    }

    @GetMapping(value = "/b/hello")
    @ResponseBody
    public String helloWorld2() {
        return helloService.hello();
    }

    @GetMapping(value = "/b/secret")
    @ResponseBody
    public String helloSecret() {
        return helloService.helloSecret();
    }

}
