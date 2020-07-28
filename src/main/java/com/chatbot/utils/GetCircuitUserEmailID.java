package com.chatbot.utils;

import com.chatbot.models.ConversationDetails;
import com.httpclients.GetCircuitTokenHttpClient;
import com.httpclients.CircuitGETHttpClient;

public class GetCircuitUserEmailID {
	

	public String getEmailID(ConversationDetails submittedFormDetails) {

		System.out.println("Inside GetUserInfoRequestBuilder Class :  getUserEmailID()");

		String httpurl = new CircuitRequestURLBuilder()
				.getBaseUrlToGetUserEmailID(submittedFormDetails.getSubmitFormData().getSubmitterId());

		// Getting Circuit Access Token
		String token = new GetCircuitTokenHttpClient().getAccessToken();
		System.out.println("circuitToken : " + token);
		
		String email = new CircuitGETHttpClient().getUserEmailID(httpurl,token);
		System.out.println("Circuit User Email ID - " + email);
		
		return email;
		

	}
}
