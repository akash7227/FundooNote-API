package com.akash.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akash.Jms.JmsProducer;
import com.akash.controller.UserController;
import com.akash.dao.UserDao;
import com.akash.model.Note;
import com.akash.model.User;
import com.google.gson.Gson;

@Service("userService")
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	TokenService tokenService;
	@Autowired
	RedisService redisService;

	@Autowired
	private EmailService emailService;

	private static Logger log = Logger.getLogger(UserService.class);

	@Transactional
	public void addUser(User user) {
		Gson gson = new Gson();
		String json = gson.toJson(user);
		log.warn("json data is :" + json);

		userDao.addUser(user);
		JmsProducer.Producer(json);

	}

	public String getUserByLogin(String user_email, String user_password) {
		String token = null;

		User found = userDao.getUserByLogin(user_email, user_password);

		if (found != null) {
			token = tokenService.createJWT(Integer.toString(found.getUser_id()), "Fundoonote", "JwtToken", 200000);
			String Check = redisService.redis(token, Integer.toString(found.getUser_id()));
		}
		return token;
	}

	public User getUserByUserEmail(String user_email) {
		// TODO Auto-generated method stub
		return userDao.getUserByUserEmail(user_email);
	}

	@Transactional
	public void saveToken(User found) {
		HttpServletRequest request = null;
		HttpServletResponse response;

		String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getServletContext().getContextPath();
		System.out.println("appurl" + appUrl);

		System.out.println(request.getServletContext().getContextPath()); /// springexample

		// Email message
		SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
		passwordResetEmail.setFrom("akashkumarsingh57@gmail.com");
		passwordResetEmail.setTo(found.getUser_email());
		passwordResetEmail.setSubject("Password Reset Request");
		passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl + "/reset?resetToken="
				+ found.getResetToken());

		int check = userDao.saveToken(found);
		if (check == 1) {
			emailService.sendEmail(passwordResetEmail);

		}

	}

	public User findUserByResetToken(String resetToken) {

		// TODO Auto-generated method stub

		return userDao.findUserByResetToken(resetToken);

	}

	public void setPassword(User user) {
		// TODO Auto-generated method stub
		userDao.setPassword(user);

	}

	public boolean getUserById(int user_id) {
		System.out.println("user_id" + user_id);
		boolean check = userDao.getUserById(user_id);
		if (check == true) {

			return true;
		}

		return false;
		// TODO Auto-generated method stub

	}

	public void addShareNote(int user_id, int note_id, int shareuser_id) {
		userDao.insertShareNote(user_id, note_id, shareuser_id);

	}

}
