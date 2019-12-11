package com.github.dragonetail.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * 角色实体
 *
 * @author sunyx
 */
@Getter
@Setter
@Entity
public class Role implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String code;

	private String name;

	private String description;

	@ManyToMany
	@JoinTable(name = "role_authority",
			joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private Set<Authority> authorities;

	@ManyToMany
	@JoinTable(name = "user_role",
			joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private Set<User> users;


	@Override
	public String getAuthority() {
		return "ROLE_" + code;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Role role = (Role) o;
		return Objects.equals(id, role.id) &&
				Objects.equals(name, role.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}