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
package com.baeldung;

import com.baeldung.enums.UserStatus;
import com.baeldung.model.Authority;
import com.baeldung.model.OAuthClient;
import com.baeldung.model.Role;
import com.baeldung.model.User;
import com.baeldung.repository.AuthorityRepository;
import com.baeldung.repository.OAuthClientRepository;
import com.baeldung.repository.RoleRepository;
import com.baeldung.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 初始化执行
 *
 * @author zhangfb
 * @version 1.0.0.1
 * @since 2019/10/13 20:58
 */
@Slf4j
@Component
public class ApplicationSetup implements CommandLineRunner {
    @Autowired
    private OAuthClientRepository oauthClientRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
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
        adminRole.setName("Admin");
        adminRole.setDescription("Admin");
        adminRole.setAuthorities(new HashSet<>(Arrays.asList(authority)));
        roleRepository.save(adminRole);

        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setName("Admin");
        adminUser.setPassword(passwordEncoder.encode("password"));
        adminUser.setStatus(UserStatus.ACTIVE);
        adminUser.setFailedLoginAttemptCount(0);
        adminUser.setLastFailedLoginDate(null);
        adminUser.setRoles(new HashSet<>(Arrays.asList(adminRole)));

        userRepository.save(adminUser);
    }

    private void buldClient() {
        OAuthClient client = new OAuthClient();
        client.setClientId("client-a");
        client.setClientSecret(passwordEncoder.encode("secret"));

        client.setResourceIds(new HashSet<>(Arrays.asList("uaa")));

        //TODO 含义详解
        client.setAuthorizedGrantTypes(new HashSet<>(
                Arrays.asList("password", "authorization_code",
                        "refresh_token", "implicit")));

        //TODO 含义详解
        client.setScope(new HashSet<>(Arrays.asList(
                "read", "write", "trust")));
        client.setSecretRequired(true);
        client.setAccessTokenValiditySeconds(50000);
        client.setRefreshTokenValiditySeconds(50000);
        client.setScoped(false);

        oauthClientRepository.save(client);
    }

}
