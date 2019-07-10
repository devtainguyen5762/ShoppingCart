package com.example.HotelManagement.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Enumerated(EnumType.STRING)
	@NaturalId
	@Column(length = 60)
	private RoleName name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public RoleName getName() {
		return name;
	}

	public void setName(RoleName name) {
		this.name = name;
	}

}
