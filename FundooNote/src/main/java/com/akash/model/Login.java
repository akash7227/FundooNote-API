package com.akash.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class Login {

	@NotEmpty(message = "user email can not be empty")
	@Email(message = "user email is not valid")
	private String user_email;

	@NotEmpty(message = "Please enter your password.")
	private String user_password;

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	@Override
	public String toString() {
		return "Login [user_email=" + user_email + ", user_password=" + user_password + "]";
	}

}
