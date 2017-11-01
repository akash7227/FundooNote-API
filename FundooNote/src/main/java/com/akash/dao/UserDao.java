package com.akash.dao;

import com.akash.model.User;

public interface UserDao {
	void addUser(User user);

	User getUserByUserEmail(String user_email);

	User getUserByLogin(String user_email, String user_password);

	int saveToken(User signup);

	User findUserByResetToken(String resetToken);

	void setPassword(User signup);

	boolean getUserById(int user_id);

	void insertShareNote(int user_id, int note_id, int shareuser_id);

}
