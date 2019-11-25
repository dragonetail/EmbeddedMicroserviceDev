package com.baeldung.config;

import com.baeldung.config.oauth2.AuthTokenEnhancer;
import com.baeldung.config.oauth2.CustomClaimAccessTokenConverter;
import com.baeldung.service.OAuthClientDetailsService;
import com.baeldung.service.OAuthUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 */
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final ApplicationProperties applicationProperties;
    private final AuthenticationManager authenticationManager;
    private final AuthTokenEnhancer authTokenEnhancer;
    private final CustomClaimAccessTokenConverter customClaimAccessTokenConverter;
    private final OAuthUserDetailsService oauthUserDetailsService;
    private final OAuthClientDetailsService oauthClientDetailsService;


    @Autowired
    public AuthorizationServerConfig(ApplicationProperties applicationProperties,
                                     AuthenticationManager authenticationManager,
                                     AuthTokenEnhancer authTokenEnhancer,
                                     CustomClaimAccessTokenConverter customClaimAccessTokenConverter,
                                     DataSource dataSource,
                                     OAuthUserDetailsService oauthUserDetailsService,
                                     OAuthClientDetailsService oauthClientDetailsService) {
        this.applicationProperties = applicationProperties;
        this.authenticationManager = authenticationManager;
        this.authTokenEnhancer = authTokenEnhancer;
        this.customClaimAccessTokenConverter = customClaimAccessTokenConverter;
        this.oauthUserDetailsService = oauthUserDetailsService;
        this.oauthClientDetailsService = oauthClientDetailsService;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer configurer) throws Exception {
        configurer
                .authenticationManager(authenticationManager)
                .userDetailsService(oauthUserDetailsService)
                .tokenServices(tokenServices())
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(oauthClientDetailsService);
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(applicationProperties.getAuth().getKfName()),
                applicationProperties.getAuth().getKsPass().toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("demo"));
        converter.setAccessTokenConverter(customClaimAccessTokenConverter);
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {

        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(authTokenEnhancer, accessTokenConverter()));
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);

        defaultTokenServices.setReuseRefreshToken(false);
        defaultTokenServices.setAccessTokenValiditySeconds(applicationProperties.getAuth().getDefaultAccessTokenTimeout());
        defaultTokenServices.setRefreshTokenValiditySeconds(applicationProperties.getAuth().getDefaultRefreshTokenTimeout());
        defaultTokenServices.setClientDetailsService(oauthClientDetailsService);

        defaultTokenServices.setAuthenticationManager(authentication -> {
            UserDetails userDetails = oauthUserDetailsService.loadUserByUsername(authentication.getName());
            PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(authentication.getName(), authentication.getCredentials(), userDetails.getAuthorities());
            token.setDetails(userDetails);
            return token;
        });

        return defaultTokenServices;
    }
}
