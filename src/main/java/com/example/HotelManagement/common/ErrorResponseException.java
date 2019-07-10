package com.example.HotelManagement.common;

import com.example.HotelManagement.Response.ApiResponse;

public class ErrorResponseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1323232323232323L;
	private ApiResponse res;

	public ErrorResponseException(ApiResponse result) {
		super();
		this.res = result;
	}

	public ErrorResponseException(ApiResponse result, Throwable cause) {
		super(cause);
		this.res = result;
	}

	public ErrorResponseException(Exception e) {
	}

	public ApiResponse getError() {
		return this.res;
	}
}
