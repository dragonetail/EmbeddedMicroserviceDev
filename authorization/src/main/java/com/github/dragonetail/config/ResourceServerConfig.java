package com.github.dragonetail.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 资源服务配置
 *
 * @author sunyx
 */
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final DefaultTokenServices tokenServices;
    private final TokenStore tokenStore;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public ResourceServerConfig(DefaultTokenServices tokenServices, TokenStore tokenStore, ApplicationProperties applicationProperties) {
        this.tokenServices = tokenServices;
        this.tokenStore = tokenStore;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer configurer) {
        configurer
                .resourceId(applicationProperties.getAuth().getResourceId())
                .tokenServices(tokenServices)
                .tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/me", "/api/user").authenticated()
                .antMatchers("/api/testAuth").hasAuthority("test")
                .antMatchers("/api/index").permitAll()
                .antMatchers("/**").denyAll();
    }
}
