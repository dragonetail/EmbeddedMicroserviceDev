package com.baeldung.repository;

import com.baeldung.model.Role;
import com.baeldung.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}