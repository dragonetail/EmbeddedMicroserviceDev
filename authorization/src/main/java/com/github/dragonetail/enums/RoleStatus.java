package com.github.dragonetail.enums;

import lombok.Getter;

/**
 * 角色状态
 *
 * @author sunyx
 */
public enum RoleStatus {
	ACTIVE("Active", "A"),
	INACTIVE("Inactive", "I"),
	DELETED("Deleted", "D");

	@Getter
	private String label;

	@Getter
	private String value;

	private RoleStatus(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public static RoleStatus getEnum(String value) {
		for (RoleStatus item : RoleStatus.values()) {
			if (item.getValue().equalsIgnoreCase(value)) {
				return item;
			}
		}
		return null;
	}

}