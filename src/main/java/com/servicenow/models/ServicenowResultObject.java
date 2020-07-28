package com.servicenow.models;

public class ServicenowResultObject {
	
	private Result result ;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ServicenowPostRequestDetails [result=" + result + "]";
	}
}
