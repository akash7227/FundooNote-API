package com.akash.TaskExecutor;

import org.springframework.mail.SimpleMailMessage;
import com.akash.model.User;
import com.akash.service.EmailService;

public class Task implements Runnable {
	EmailService emp;
	User user;

	public Task(User user, EmailService emp) {
		this.user = user;
		this.emp = emp;
		System.out.println("const");

	}

	public void run() {
		System.out.println(user.getUser_email());

		SimpleMailMessage sendEmail = new SimpleMailMessage();
		sendEmail.setFrom("akashkumarsingh57@gmail.com");
		sendEmail.setTo(user.getUser_email());
		sendEmail.setSubject("you successfully register in fundooNote ");
		sendEmail.setText("Hello " + user.getUser_name() + "Welcome to fundoNote");
		emp.sendEmail(sendEmail);
	}

}
