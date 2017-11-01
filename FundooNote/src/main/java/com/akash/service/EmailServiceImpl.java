package com.akash.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public void sendEmail(SimpleMailMessage user_email) {
		System.out.println("check email is " + user_email);
		mailSender.send(user_email);
	}

}
