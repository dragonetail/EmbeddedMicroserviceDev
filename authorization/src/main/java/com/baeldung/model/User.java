package com.baeldung.model;

import com.baeldung.enums.UserStatus;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 */
@Data
@Entity
public class User implements UserDetails {
    private static final long failedLoginAttemptAccountLockTimeout =900;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private UserStatus status;

    private Integer failedLoginAttemptCount;

    private LocalDateTime lastFailedLoginDate;

    private String name;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> authorities = new HashSet<>();
        if (roles != null) {
            roles.forEach(role -> {
                if (role.getAuthorities() != null) {
                    authorities.addAll(role.getAuthorities());
                }
            });
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status != UserStatus.DELETED;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (status == UserStatus.ACTIVE) {
            return true;
        } else if (status == UserStatus.TEMP_LOCKED_BAD_CREDENTIALS && lastFailedLoginDate != null) {
            return lastFailedLoginDate.plusSeconds(failedLoginAttemptAccountLockTimeout)
                    .isBefore(LocalDateTime.now());
//            return lastFailedLoginDate.plusSeconds(StaticApplicationContext.getApplicationContext()
//                    .getBean(ApplicationProperties.class).getAuth().getFailedLoginAttemptAccountLockTimeout())
//                    .isBefore(LocalDateTime.now());
        } else {
            return false;
        }
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status != UserStatus.DELETED;
    }

    @Override
    public boolean isEnabled() {
        return !(status == UserStatus.INACTIVE || status == UserStatus.PENDING_ACTIVATION || status == UserStatus.CREATED);
    }
}