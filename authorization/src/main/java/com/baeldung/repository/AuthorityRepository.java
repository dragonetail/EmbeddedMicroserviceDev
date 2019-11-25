package com.baeldung.repository;

import com.baeldung.model.Authority;
import com.baeldung.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Long> {
}