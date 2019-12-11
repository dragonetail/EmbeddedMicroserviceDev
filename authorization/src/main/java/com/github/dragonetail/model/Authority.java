package com.github.dragonetail.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * 权限实体
 *
 * @author sunyx
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String code;

	private String name;



	@Override
	public String getAuthority() {
		return code;
	}

}