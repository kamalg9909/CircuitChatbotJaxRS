package com.servicenow.models;

public class SnowHTTPOutput {
	
	private String httpresponseStatus;
	private String statusString;
	private Result result;
	
	
	public String getHttpresponseStatus() {
		return httpresponseStatus;
	}
	public void setHttpresponseStatus(String httpresponseStatus) {
		this.httpresponseStatus = httpresponseStatus;
	}
	
	public String getStatusString() {
		return statusString;
	}
	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	
	

}
