package com.chatbot.models;

public class CircuitSendMessageRequestObject {
	
	String baseURL = "";
	String token = "" ;
	String subject = "";
	String contentMessage = "";
	String formMetaData = "";
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getBaseURL() {
		return baseURL;
	}
	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}
	
	public String getContentMessage() {
		return contentMessage;
	}
	public void setContentMessage(String contentMessage) {
		this.contentMessage = contentMessage;
	}
	public String getFormMetaData() {
		return formMetaData;
	}
	public void setFormMetaData(String formMetaData) {
		this.formMetaData = formMetaData;
	}
	@Override
	public String toString() {
		return "CircuitSendMessageRequestObject : "
				+ "\n" + "baseURL=" + baseURL 
				+ "\n" + "token=" + token
				+ "\n" + "subject=" + subject
				+ "\n" + "contentMessage=" + contentMessage;
	}
	
	
	

}
