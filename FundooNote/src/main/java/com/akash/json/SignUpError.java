package com.akash.json;

import java.util.List;

import org.springframework.validation.FieldError;

public class SignUpError extends Response {
	
	List<FieldError> errorlist;

	public List<FieldError> getErrorlist() {
		return errorlist;
	}

	public void setErrorlist(List<FieldError> errorlist) {
		this.errorlist = errorlist;
	}
	
}
