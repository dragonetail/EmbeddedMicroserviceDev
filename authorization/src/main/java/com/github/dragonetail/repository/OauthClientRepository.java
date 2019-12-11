package com.github.dragonetail.repository;

import com.github.dragonetail.model.OauthClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * OAuth2认证客户端DB访问
 *
 * @author sunyx
 */
@Repository
public interface OauthClientRepository extends JpaRepository<OauthClient, String> {
}