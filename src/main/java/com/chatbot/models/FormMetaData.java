package com.chatbot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FormMetaData {
	
	private String id;
	private String title;
	
	public FormMetaData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "FormMetaData" 
				+ "\n" + "Id=" + id 
				+ "\n" + "Title=" + title ;
	}
	
	
	
	

}
