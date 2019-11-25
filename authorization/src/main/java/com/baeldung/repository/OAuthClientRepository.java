package com.baeldung.repository;

import com.baeldung.model.OAuthClient;
import com.baeldung.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 */
@Repository
public interface OAuthClientRepository extends JpaRepository<OAuthClient, String> {
}