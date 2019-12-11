package com.github.dragonetail.repository;

import com.github.dragonetail.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 角色DB访问
 *
 * @author sunyx
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}