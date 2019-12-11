package com.github.dragonetail.repository;

import com.github.dragonetail.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 权限DB访问
 *
 * @author sunyx
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Long> {
}