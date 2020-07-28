package com.chatbot.models;

import java.util.List;

public class SubmitFormData {
	
	private String itemId;
	private String formId;
	private String submitterId;
	private List<Data> data = null;
	
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getSubmitterId() {
		return submitterId;
	}
	public void setSubmitterId(String submitterId) {
		this.submitterId = submitterId;
	}
	public List<Data> getData() {
		return data;
	}
	public void setData(List<Data> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "SubmitFormData [itemId=" + itemId + ", formId=" + formId + ", submitterId=" + submitterId + ", data="
				+ data + "]";
	}
	
	

}
