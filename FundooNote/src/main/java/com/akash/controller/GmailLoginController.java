package com.akash.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.akash.json.Response;
import com.akash.model.Gmail;
import com.akash.model.GmailProfile;
import com.akash.model.User;
import com.akash.service.UserService;

@Controller
public class GmailLoginController {
	private static Logger log = Logger.getLogger(GmailLoginController.class);
	@Autowired
	private UserService userService;

	@RequestMapping("/GmailLogin")
	public void GmailLogin(HttpServletRequest request, HttpServletResponse response) {

		log.warn("inside");
		String url = request.getRequestURL().toString();
		log.warn("url >>" + url);
		String apiRedirecturl = url.substring(0, url.lastIndexOf('/'));
		log.warn("next url >>" + apiRedirecturl);
		String stateCode = UUID.randomUUID().toString();
		request.getSession().setAttribute("State", stateCode);
		String gmailUrl = Gmail.getGmailUrl(apiRedirecturl, stateCode);
		log.warn("gmail" + gmailUrl);

		try {
			response.sendRedirect(gmailUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;

	}

	@RequestMapping("/postGmail")
	public @ResponseBody ResponseEntity<Response> postGmailLogin(HttpServletRequest request,
			HttpServletResponse response, User found, HttpSession session) throws IOException {
		System.out.println("inside post gmail");
		String sessionState = (String) request.getSession().getAttribute("State");
		String stateFromGmail = request.getParameter("state");
		System.out.println("SESSION STATE " + sessionState);
		System.out.println("STATE FROM GMAIL " + stateFromGmail);
		if (sessionState == null || !sessionState.equals(stateFromGmail)) {
			// System.out.println("TRUE ");
			response.sendRedirect("GmailLogin");
			// return;
		}
		String error = request.getParameter("error");
		if (error != null && !error.trim().isEmpty()) {
			response.sendRedirect("");

		}

		String authcode = request.getParameter("code");
		String urlfromGmail = request.getRequestURL().toString();
		String apiredirectUrl = urlfromGmail.substring(0, urlfromGmail.lastIndexOf("/"));
		log.warn("apiredirectUrl :" + apiredirectUrl);
		GmailProfile profile = Gmail.authuser(authcode, apiredirectUrl);
		log.warn(profile.getEmails().get(0).getValue());
		log.warn("name is " + profile.getDisplayName());
		found = userService.getUserByUserEmail(profile.getEmails().get(0).getValue());
		log.warn("sign is >>" + found);
		if (found == null) {
			log.warn("null");
			found = new User();
			found.setUser_name(profile.getDisplayName());
			found.setUser_email(profile.getEmails().get(0).getValue());

			log.warn("dekho signup" + found);
			userService.insertUser(found);

		} else {
			log.warn("signup is " + found);
		}
		return null;
	}

}
