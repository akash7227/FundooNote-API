package com.akash.model;

import java.util.List;

public class Collaborater {
	private String user_id;
	private String id;
	private List share_user_id;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List getShare_user_id() {
		return share_user_id;
	}

	public void setShare_user_id(List share_user_id) {
		this.share_user_id = share_user_id;
	}

	@Override
	public String toString() {
		return "Collaborater [user_id=" + user_id + ", id=" + id + ", share_user_id=" + share_user_id + "]";
	}

}
