package com.github.dragonetail.config;

import com.github.dragonetail.config.oauth2.AuthTokenEnhancer;
import com.github.dragonetail.config.oauth2.CustomClaimAccessTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
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
 * 认证服务配置
 *
 * @author sunyx
 */
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final ApplicationProperties applicationProperties;
    private final AuthenticationManager authenticationManager;
    private final AuthTokenEnhancer authTokenEnhancer;
    private final CustomClaimAccessTokenConverter customClaimAccessTokenConverter;
    private final UserDetailsService userDetailsService;
    private final ClientDetailsService clientDetailsService;


    @Autowired
    public AuthorizationServerConfig(ApplicationProperties applicationProperties,
                                     AuthenticationManager authenticationManager,
                                     AuthTokenEnhancer authTokenEnhancer,
                                     CustomClaimAccessTokenConverter customClaimAccessTokenConverter,
                                     DataSource dataSource,
                                     UserDetailsService userDetailsService,
                                     ClientDetailsService clientDetailsService) {
        this.applicationProperties = applicationProperties;
        this.authenticationManager = authenticationManager;
        this.authTokenEnhancer = authTokenEnhancer;
        this.customClaimAccessTokenConverter = customClaimAccessTokenConverter;
        this.userDetailsService = userDetailsService;
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer configurer) throws Exception {
        //设置OAuth认证配置
        configurer
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenServices(tokenServices())
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //设置Client获取服务
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        //设置OAuth认证接口访问安全
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients()
                .passwordEncoder(new BCryptPasswordEncoder());
    }


    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        //设置Token服务
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(authTokenEnhancer, accessTokenConverter()));
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);

        defaultTokenServices.setReuseRefreshToken(false);
        defaultTokenServices.setAccessTokenValiditySeconds(applicationProperties.getAuth().getDefaultAccessTokenTimeout());
        defaultTokenServices.setRefreshTokenValiditySeconds(applicationProperties.getAuth().getDefaultRefreshTokenTimeout());

        defaultTokenServices.setClientDetailsService(clientDetailsService);

        defaultTokenServices.setAuthenticationManager(authentication -> {
            UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
            PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(authentication.getName(), authentication.getCredentials(), userDetails.getAuthorities());
            token.setDetails(userDetails);
            return token;
        });

        return defaultTokenServices;
    }

    @Bean
    @Primary
    public TokenStore tokenStore() {
        //设置Token存储，默认不存储
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    @Primary
    public JwtAccessTokenConverter accessTokenConverter() {
        //设置Token转换器
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        KeyStoreKeyFactory keyStoreKeyFactory =
//                new KeyStoreKeyFactory(new ClassPathResource(applicationProperties.getAuth().getKeystoreFile()),
//                        applicationProperties.getAuth().getKeystorePassword().toCharArray());
//        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(applicationProperties.getAuth().getKeyAlias()));
//        converter.setAccessTokenConverter(customClaimAccessTokenConverter);
//        return converter;

        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("12345678");
        //converter.setJwtClaimsSetVerifier(jwtClaimsSetVerifier());

        return converter;
    }
}