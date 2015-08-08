package com.matrimony.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sender;
	private String subject;
	private List<String> recipients;
	private String htmlBody;

	private String htmlBodyTemplateFile;
	private Map<String, String> params;

	public EmailVO() {
		recipients = new ArrayList<String>();
		params = new HashMap<String, String>();
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<String> getRecipients() {
		return recipients;
	}

	public void addRecipients(String... recipients) {
		this.recipients.addAll(Arrays.asList(recipients));
	}

	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}

	public String getHtmlBody() {
		return htmlBody;
	}

	public void setHtmlBody(String htmlBody) {
		this.htmlBody = htmlBody;
	}

	public String getHtmlBodyTemplateFile() {
		return htmlBodyTemplateFile;
	}

	public void setHtmlBodyTemplateFile(String htmlBodyTemplateFile) {
		this.htmlBodyTemplateFile = htmlBodyTemplateFile;
	}

	public Map<String, String> getParameters() {
		return params;
	}

	public void setParameters(Map<String, String> params) {
		this.params = params;
	}

	public void addParameter(String paramName, String paramValue) {
		if (this.params == null) {
			this.params = new HashMap<String, String>();
		}
		this.params.put(paramName, paramValue);
	}

}
