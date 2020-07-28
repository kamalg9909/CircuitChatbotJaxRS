package com.chatbot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Text {
	
	private String state;
	private String subject;
	private String content;
	private String formMetaData;
	private boolean isWebhookMessage;
	private String contentType;
	
	public Text() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Text(String state, String subject, String content, String formMetaData, boolean isWebhookMessage,
			String contentType) {
		super();
		this.state = state;
		this.subject = subject;
		this.content = content;
		this.formMetaData = formMetaData;
		this.isWebhookMessage = isWebhookMessage;
		this.contentType = contentType;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getFormMetaData() {
		return formMetaData;
	}
	public void setFormMetaData(String formMetaData) {
		this.formMetaData = formMetaData;
	}
	public boolean isWebhookMessage() {
		return isWebhookMessage;
	}
	public void setWebhookMessage(boolean isWebhookMessage) {
		this.isWebhookMessage = isWebhookMessage;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@Override
	public String toString() {
		return "**************************************************" 
				+ "\n" + "Text: " 
				+ "\n" + "**************************************************"
				+ "\n" + "State: " + state 
				+ "\n" + "Subject: " + subject 
				+ "\n" + "Content: " + content
				+ "\n" + "FormMetaData: " + formMetaData 
				+ "\n" + "ContentType: " + contentType
				+ "\n" + "IsWebhookMessage: " + isWebhookMessage;
	}
	
	
}
