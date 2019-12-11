package com.github.dragonetail.config.oauth2;

import com.github.dragonetail.model.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证Token增强
 *
 * @author sunyx
 */
@Component
public class AuthTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Object principal = authentication.getPrincipal();
        Map<String, Object> additionalInfo = new HashMap<>(10);
        if(principal instanceof User){
            User user = (User)principal;

            additionalInfo.put("user_id", user.getId());
            additionalInfo.put("user_status", user.getStatus().name());
        }
        additionalInfo.put("TEST", "TEST Enhancer");

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }

}