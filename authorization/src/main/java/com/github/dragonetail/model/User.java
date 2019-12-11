package com.github.dragonetail.model;

import com.github.dragonetail.StaticApplication;
import com.github.dragonetail.config.ApplicationProperties;
import com.github.dragonetail.enums.UserStatus;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 用户实体
 *
 * @author sunyx
 */
@Data
@Entity
public class User implements UserDetails, Serializable {

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
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (roles != null) {
            roles.forEach(role -> {
                authorities.add(role);
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
            return lastFailedLoginDate.plusSeconds(StaticApplication.applicationContext
                    .getBean(ApplicationProperties.class).getAuth().getFailedLoginAttemptAccountLockTimeout())
                    .isBefore(LocalDateTime.now());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}