package com.example.HotelManagement.request;

import javax.validation.constraints.NotBlank;

public class ActiveRequest {
	@NotBlank
	private String usernameOrEmail;

	@NotBlank
	private String token;

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
