package com.akash.model;

import java.util.Date;

public class Note {
	private int id;
	private String title;
	private String description;
	private String date;
	private User user;
	private boolean archive;
	private boolean trash;
	private Date remindme;
	private boolean pin;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isArchive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	public boolean isTrash() {
		return trash;
	}

	public void setTrash(boolean trash) {
		this.trash = trash;
	}

	public Date getRemindme() {
		return remindme;
	}

	public void setRemindme(Date remindme) {
		this.remindme = remindme;
	}

	public boolean isPin() {
		return pin;
	}

	public void setPin(boolean pin) {
		this.pin = pin;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", title=" + title + ", description=" + description + ", date=" + date + ", user="
				+ user + ", archive=" + archive + ", trash=" + trash + ", remindme=" + remindme + ", pin=" + pin + "]";
	}

}
