/********************************************
 * 功能说明: 
 * 模块名称: 
 * 系统名称: 
 * 软件版权: 
 * 系统版本: 1.0.0
 * 开发人员: zhangfb
 * 开发时间: 2019/10/13 20:58
 * 审核人员: 
 * 相关文档: 
 * 修改记录: 修改日期 修改人员 修改说明
 *********************************************/
package com.github.dragonetail;

import com.github.dragonetail.enums.UserStatus;
import com.github.dragonetail.model.Authority;
import com.github.dragonetail.model.OauthClient;
import com.github.dragonetail.model.Role;
import com.github.dragonetail.model.User;
import com.github.dragonetail.repository.AuthorityRepository;
import com.github.dragonetail.repository.OauthClientRepository;
import com.github.dragonetail.repository.RoleRepository;
import com.github.dragonetail.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 初始化执行
 *
 * @author sunyx
 */
@Slf4j
@Component
public class ApplicationSetup implements CommandLineRunner {
    @Autowired
    private OauthClientRepository oauthClientRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(String... args) throws Exception {
        buildAuthorities();

        buldClient();
    }

    private void buildAuthorities() {
        Authority authority = new Authority();
        authority.setCode("test");
        authority.setName("Test Authority");
        authorityRepository.save(authority);

        Role adminRole = new Role();
        String adminName = "Admin";
        adminRole.setCode("ADMIN");
        adminRole.setName(adminName);
        adminRole.setDescription(adminName);
        adminRole.setAuthorities(new HashSet<>(Arrays.asList(authority)));
        roleRepository.save(adminRole);

        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setName(adminName);
        adminUser.setPassword(passwordEncoder.encode("password"));
        adminUser.setStatus(UserStatus.ACTIVE);
        adminUser.setFailedLoginAttemptCount(0);
        adminUser.setLastFailedLoginDate(null);
        adminUser.setRoles(new HashSet<>(Arrays.asList(adminRole)));

        userRepository.save(adminUser);
    }

    private void buldClient() {
        {
            OauthClient client = new OauthClient();
            client.setClientId("web");
            client.setClientSecret(passwordEncoder.encode(""));

            //client.setResourceIds(new HashSet<>(Arrays.asList("web")));

            client.setAuthorizedGrantTypes(new HashSet<>(
                    Arrays.asList("password", "refresh_token")));

            client.setScope(new HashSet<>(Arrays.asList("web")));
            client.setSecretRequired(true);
            client.setAccessTokenValiditySeconds(50000);
            client.setRefreshTokenValiditySeconds(50000);
            client.setScoped(false);

            oauthClientRepository.save(client);
        }
        {
            OauthClient client = new OauthClient();
            client.setClientId("mobile");
            client.setClientSecret(passwordEncoder.encode(""));

            //client.setResourceIds(new HashSet<>(Arrays.asList("web")));

            client.setAuthorizedGrantTypes(new HashSet<>(
                    Arrays.asList("password", "refresh_token")));

            client.setScope(new HashSet<>(Arrays.asList("mobile")));
            client.setSecretRequired(true);
            client.setAccessTokenValiditySeconds(50000);
            client.setRefreshTokenValiditySeconds(50000);
            client.setScoped(false);

            oauthClientRepository.save(client);
        }
        {
            OauthClient client = new OauthClient();
            client.setClientId("client-a");
            client.setClientSecret(passwordEncoder.encode("client-password"));

            client.setResourceIds(new HashSet<>(Arrays.asList("uaa")));

            client.setAuthorizedGrantTypes(new HashSet<>(
                    Arrays.asList("password", "client_credentials",
                            "refresh_token", "implicit")));
            ///"authorization_code"

            client.setScope(new HashSet<>(Arrays.asList(
                    "read", "write", "trust")));
            client.setSecretRequired(true);
            client.setAccessTokenValiditySeconds(50000);
            client.setRefreshTokenValiditySeconds(50000);
            client.setScoped(false);

            oauthClientRepository.save(client);
        }
    }

}
