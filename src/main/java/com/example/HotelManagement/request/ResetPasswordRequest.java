package com.example.HotelManagement.request;

import javax.validation.constraints.NotBlank;

public class ResetPasswordRequest {
	@NotBlank
	private String email;

	@NotBlank
	private String token;
	
	@NotBlank
	private String password;



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
