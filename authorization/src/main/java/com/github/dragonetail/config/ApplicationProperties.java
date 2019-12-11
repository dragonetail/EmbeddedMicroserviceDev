package com.github.dragonetail.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 认证Token增强
 *
 * @author sunyx
 */
@Data
@Configuration
@ConfigurationProperties("app")
public class ApplicationProperties {

    private Auth auth;

    @Data
    public static class Auth {

        private String resourceId;

        private String keystoreFile;
        private String keyAlias;
        private String keystorePassword;

        private Integer defaultAccessTokenTimeout;
        private Integer defaultRefreshTokenTimeout;
        private Integer failedLoginAttemptAccountLockTimeout;
        private Integer maxFailedLoginAttemptsForAccountLock;

    }
}