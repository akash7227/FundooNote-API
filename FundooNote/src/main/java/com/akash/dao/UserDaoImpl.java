package com.akash.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akash.mappers.UserMapper;
import com.akash.model.User;

@Service("userDao")
public class UserDaoImpl implements UserDao {
	@Autowired
	private UserMapper userMapper;

	public void addUser(User user) {
		System.out.println("in side userdaoImp user is :" + user);
		userMapper.insertUser(user);

	}

	public User getUserByLogin(String user_email, String user_password) {
		System.out.println("user_email is  " + user_email + "user_password is " + user_password);
		User user = userMapper.getUserByUserEmail(user_email);
		System.out.println("signup is " + user);

		if (user != null && user.getUser_password().equals(user_password)) {
			return user;
		}

		return null;
	}

	public User getUserByUserEmail(String user_email) {
		User user = userMapper.getUserByUserEmail(user_email);

		if (user != null) {
			return user;
		}

		return user;
	}

	public int saveToken(User user) {
		int check = userMapper.insertToken(user);
		if (check == 1) {
			return 1;
		}
		return 0;

	}

	public User findUserByResetToken(String resetToken) {
		User user = userMapper.findUserByResetToken(resetToken);
		return user;
	}

	public void setPassword(User user) {
		System.out.println("setpassword");
		userMapper.updatePassword(user);

	}

	public boolean getUserById(int user_id) {
		System.out.println("in side imp" + user_id);
		boolean check = userMapper.getUserById(user_id);

		if (check == true) {
			return true;
		}
		return false;

	}

	public void insertShareNote(int user_id, int note_id, int shareuser_id) {
		System.out.println(user_id + " " + note_id + " " + shareuser_id);
		userMapper.insertShareNote(user_id, note_id, shareuser_id);

	}

}
