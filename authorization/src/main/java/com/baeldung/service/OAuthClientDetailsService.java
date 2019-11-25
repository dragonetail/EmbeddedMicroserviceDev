package com.baeldung.service;

import com.baeldung.model.OAuthClient;
import com.baeldung.repository.OAuthClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
@Service
public class OAuthClientDetailsService implements ClientDetailsService {
    @Autowired
    private OAuthClientRepository oauthClientRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        OAuthClient client = oauthClientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalStateException("没有找到clientId。"));

        String resourceIds = client.getResourceIds().stream().collect(Collectors.joining(","));
        String scopes = client.getScope().stream().collect(Collectors.joining(","));
        String grantTypes = client.getAuthorizedGrantTypes().stream().collect(Collectors.joining(","));
        String authorities = client.getAuthorities().stream().collect(Collectors.joining(","));

        BaseClientDetails base = new BaseClientDetails(client.getClientId(),
                resourceIds, scopes, grantTypes, authorities);
        base.setClientSecret(client.getClientSecret());
        base.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
        base.setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
        base.setAdditionalInformation(client.getAdditionalInformation());
        base.setAutoApproveScopes(client.getScope());
        return base;
    }
}