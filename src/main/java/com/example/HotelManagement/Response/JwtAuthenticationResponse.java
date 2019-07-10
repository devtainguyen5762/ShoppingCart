package com.example.HotelManagement.Response;

public class JwtAuthenticationResponse {
	private String accessToken;
	private String refreshToken;
	private String tokenType = "Bearer";
	private Long expiresInMsec;

	public JwtAuthenticationResponse(String accessToken) {
		this.accessToken = accessToken;
	
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Long getExpiresInMsec() {
		return expiresInMsec;
	}

	public void setExpiresInMsec(Long expiresInMsec) {
		this.expiresInMsec = expiresInMsec;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
}
