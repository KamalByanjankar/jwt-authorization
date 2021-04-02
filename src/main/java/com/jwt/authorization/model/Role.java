package com.jwt.authorization.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class Role {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private ERole role_name;

	public Role() {
		super();
	}

	public Role(ERole role_name) {
		super();
		this.role_name = role_name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ERole getRole_name() {
		return role_name;
	}

	public void setRole_name(ERole role_name) {
		this.role_name = role_name;
	}
	
	
}
