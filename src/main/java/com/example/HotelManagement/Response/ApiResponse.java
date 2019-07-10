package com.example.HotelManagement.Response;

import java.util.HashMap;

public class ApiResponse {
	private Boolean success;
	private String message;
	private Object data;

	public ApiResponse(Boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public ApiResponse(Boolean success, Object data, String message) {
		this.success = success;
		this.data = data == null ? new HashMap() : data;
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
