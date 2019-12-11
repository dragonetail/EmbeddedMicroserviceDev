package com.github.dragonetail.service;

import com.github.dragonetail.model.OauthClient;
import com.github.dragonetail.repository.OauthClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 客户端信息服务
 *
 * @author sunyx
 */
@Slf4j
@Service
@Primary
public class OauthClientDetailsServiceImpl implements ClientDetailsService {
    @Autowired
    private OauthClientRepository oauthClientRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        OauthClient client = oauthClientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalStateException("没有找到clientId: " + clientId));

        String resourceIds = StringUtils.collectionToCommaDelimitedString(client.getResourceIds());
        String scopes = StringUtils.collectionToCommaDelimitedString(client.getScope());
        String grantTypes = StringUtils.collectionToCommaDelimitedString(client.getAuthorizedGrantTypes());
        String authorities = StringUtils.collectionToCommaDelimitedString(client.getAuthorities());

        BaseClientDetails base = new BaseClientDetails(client.getClientId(),
                resourceIds, scopes, grantTypes, authorities);
        base.setClientSecret(client.getClientSecret());
        base.setClientSecret(null);
        base.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
        base.setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
        base.setAdditionalInformation(client.getAdditionalInformation());
        base.setAutoApproveScopes(client.getScope());
        return base;
    }
}