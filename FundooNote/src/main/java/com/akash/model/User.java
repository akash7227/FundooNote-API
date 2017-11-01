package com.akash.model;

import java.util.List;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class User {
	private int user_id;

	@NotEmpty(message = "user name can not be empty")
	@Pattern(regexp = ("^[A-Za-z]*$"), message = "name must contain letter.")
	private String user_name;

	@NotEmpty(message = "Email can not be empty")
	@Email
	private String user_email;

	@NotEmpty(message = "address can not be empty")
	private String user_address;

	@NotEmpty(message = "password can not be empty")
	@Size(min = 6, max = 15, message = "Your password must between 6 and 15 characters")
	private String user_password;

	private String resetToken;

	private List share_user_id;

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_address() {
		return user_address;
	}

	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public List getShare_user_id() {
		return share_user_id;
	}

	public void setShare_user_id(List share_user_id) {
		this.share_user_id = share_user_id;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_name=" + user_name + ", user_email=" + user_email
				+ ", user_address=" + user_address + ", user_password=" + user_password + ", resetToken=" + resetToken
				+ ", share_user_id=" + share_user_id + "]";
	}

}
