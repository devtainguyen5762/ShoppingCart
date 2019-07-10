package com.example.HotelManagement.model;

import java.time.Instant;

import javax.persistence.*;

@Entity
@Table(name = "refresh_tokens")
public class JwtRefreshToken {

	@Id
	private String token;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="user_id",nullable = false)
	private User user;
	
	private Instant expirationDateTime;
	public JwtRefreshToken() {}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Instant getExpirationDateTime() {
		return expirationDateTime;
	}
	public void setExpirationDateTime(Instant expirationDateTime) {
		this.expirationDateTime = expirationDateTime;
	}
	
}
