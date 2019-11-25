package com.baeldung.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * 
 *
 */
@Getter
@Setter
@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

}