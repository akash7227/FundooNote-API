package com.akash.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.akash.json.LoginResponse;
import com.akash.json.Response;
import com.akash.json.SignUpError;
import com.akash.model.Login;
import com.akash.model.Note;
import com.akash.model.User;
import com.akash.service.ElasticSearchService;
import com.akash.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private TaskExecutor taskExecutor;

	@Autowired
	private ElasticSearchService elasticSearchService;

	private static Logger log = Logger.getLogger(UserController.class);

	@RequestMapping(value = "saveUser", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> signup(@Valid @RequestBody User user, BindingResult result) {
		Response resp;

		log.warn("inside saveUser");
		log.warn("user " + user);
		if (result.hasErrors()) {

			log.warn("check" + result.getErrorCount());
			SignUpError error = new SignUpError();
			error.setStatus(-1);
			error.setMsg("invalid  details");
			error.setErrorlist(result.getFieldErrors());
			return new ResponseEntity<Response>(error, HttpStatus.BAD_REQUEST);

		}

		userService.addUser(user);
		log.info("registered");

		resp = new Response();
		resp.setStatus(1);
		resp.setMsg("User registered successfully!!!");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);

	}

	@RequestMapping(value = "/UserLogin", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> login(@Valid @RequestBody Login login, BindingResult result) {

		log.warn("login is " + login);
		Response resp;
		LoginResponse lresp;

		if (result.hasErrors()) {
			SignUpError error = new SignUpError();
			error.setStatus(-1);
			error.setMsg("invalid login details");
			error.setErrorlist(result.getFieldErrors());

			return new ResponseEntity<Response>(error, HttpStatus.BAD_REQUEST);

		} else {
			String token = userService.getUserByLogin(login.getUser_email(), login.getUser_password());
			log.warn("User found and generate token " + token);
			resp = new Response();

			if (token != null) {

				resp.setStatus(1);
				resp.setMsg("User login successfully!!!");
				MultiValueMap<String, String> map = new HttpHeaders();
				map.add("jwt", token);
				return new ResponseEntity<Response>(resp, map, HttpStatus.OK);

			} else {

				resp.setStatus(1);
				resp.setMsg("sorry your email or password is wrong!!!");
				return new ResponseEntity<Response>(resp, HttpStatus.OK);
			}

		}

	}

	@RequestMapping(value = "searchNote", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> searchDatafromElasticServer(@RequestBody Note note)
			throws IOException {
		Response resp;
		System.out.println("note is " + note);

		boolean found = elasticSearchService.search(note);
		if (found == true) {
			resp = new Response();
			resp.setStatus(1);
			resp.setMsg("yes we find data from elastic search!!!");
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} else {
			resp = new Response();
			resp.setStatus(1);
			resp.setMsg("yes we didn't find data from elastic search!!!");
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "deleteDataFromElastic", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> deleteDataFromElastic(@RequestBody Note note) throws IOException {
		Response resp;
		System.out.println("note is " + note);

		boolean found = elasticSearchService.deleteDataFromElastic(note);
		if (found == true) {
			resp = new Response();
			resp.setStatus(1);
			resp.setMsg("yes your data is deleted from elastic search!!!");
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} else {
			resp = new Response();
			resp.setStatus(1);
			resp.setMsg("yes data didn't deleted  from elastic search!!!");
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "forgot", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> ForgotPassword(@RequestParam("user_email") String user_email) {
		Response resp = null;

		// Lookup user in database by e-mail
		User found = userService.getUserByUserEmail(user_email);
		System.out.println("found" + found);

		if (found == null) {
			resp.setStatus(1);
			resp.setMsg("sorry We didn't find an account for that e-mail address.");
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} else {

			// Generate random 36-character string token for reset password
			found.setResetToken(UUID.randomUUID().toString());

			// Save token to database
			userService.saveToken(found);

			resp.setStatus(1);
			resp.setMsg("for forgot password we have sent wrong!!!");
			return new ResponseEntity<Response>(resp, HttpStatus.OK);

		}

	}

	@RequestMapping("/reset")
	public ModelAndView displayResetPasswordPage(ModelAndView modelAndView,
			@RequestParam("resetToken") String resetToken) {
		System.out.println("resettoken is " + resetToken);
		User user = userService.findUserByResetToken(resetToken);

		System.out.println("user" + user);
		if (user != null) {
			// Token found in DB
			modelAndView.addObject("resetToken", resetToken);

		} else {
			// Token not found in
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
		}

		modelAndView.setViewName("resetPassword");
		return modelAndView;
	}

	// Process reset password form
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ModelAndView setNewPassword(@RequestParam Map<String, String> requestParams, ModelAndView modelAndView,
			User user) {

		String token = requestParams.get("resetToken");
		user = userService.findUserByResetToken(token);

		// This should always be non-null but we check just in case
		if (user != null) {
			System.out.println("new password " + requestParams.get("newPassword"));
			System.out.println("user" + user);
			String password = requestParams.get("newPassword");
			user.setUser_password(password);
			user.setResetToken(token);
			userService.setPassword(user);

			user.setResetToken("");
			System.out.println("last" + user);
			userService.saveToken(user);

		} else {
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
			modelAndView.setViewName("resetPassword");
		}

		return modelAndView;
	}

}
