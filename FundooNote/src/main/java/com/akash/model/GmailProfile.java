package com.akash.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GmailProfile {
	private String displayName;
	private List<Emails> emails;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<Emails> getEmails() {
		return emails;
	}

	public void setEmails(List<Emails> emails) {
		this.emails = emails;
	}

	@Override
	public String toString() {
		return "GmailProfile [displayName=" + displayName + ", emails=" + emails + "]";
	}

}
