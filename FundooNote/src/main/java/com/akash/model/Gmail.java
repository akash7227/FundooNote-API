package com.akash.model;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class Gmail {

	private static String Scope = "profile email";
	private static String Gmail_CLIENT_ID = "95803056311-gjsedmifv6ijbro6o16ie5vkjjuve81q.apps.googleusercontent.com";
	private static String Gmail_SCERET_ID = "32dYbC91dsAmtbZTypdpX3RG";
	private static String Gmail_REDIRECT_URI = "/postGmail";
	private static String Gmail_URL = "https://accounts.google.com/o/oauth2/v2/auth?client_id=%s&redirect_uri=%s&state=%s&"
			+ "response_type=code&scope=%s&approval_prompt=force&access_type=offline";
	// THIS is post url
	private static String sGmail_ACCESS_TOKEN_URL = "https://www.googleapis.com/oauth2/v4/token";

	// Access token in header
	private static String sGmail_GET_USER_URL = "https://www.googleapis.com/plus/v1/people/me";

	public static String getGmailUrl(String apiRedirecturl, String stateCode) {
		System.out.println("inside getGmail url");
		apiRedirecturl = apiRedirecturl + Gmail_REDIRECT_URI;
		return new String().format(Gmail_URL, new String[] { Gmail_CLIENT_ID, apiRedirecturl, stateCode, Scope });

	}

	public static GmailProfile authuser(String authcode, String apiredirectUrl) {
		String accessToken = getaccessToken(authcode, apiredirectUrl);
		System.out.println("access token is >> " + accessToken);
		return getUserProfile(accessToken);

	}

	private static GmailProfile getUserProfile(String accessToken) {
		// TODO Auto-generated method stub
		String accUserUrl = sGmail_GET_USER_URL;
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(accUserUrl);
		String headerAuth = "Bearer " + accessToken;
		Response response = target.request().header("Authorization", headerAuth).accept(MediaType.APPLICATION_JSON)
				.get();

		GmailProfile profile = response.readEntity(GmailProfile.class);
		// String string = response.readEntity(String.class);
		System.out.println(profile);

		client.close();
		return profile;
	}

	private static String getaccessToken(String authcode, String apiredirectUrl) {
		apiredirectUrl = apiredirectUrl + Gmail_REDIRECT_URI;
		String accessToken = sGmail_ACCESS_TOKEN_URL;
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(accessToken);

		Form form = new Form();
		form.param("client_id", Gmail_CLIENT_ID);
		form.param("client_secret", Gmail_SCERET_ID);
		form.param("redirect_uri", apiredirectUrl);
		form.param("code", authcode);
		form.param("grant_type", "authorization_code");

		Response response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.form(form));
		GmailToken token = response.readEntity(GmailToken.class);

		System.out.println("token is " + token);
		client.close();
		return token.getAccess_token();

	}

}
