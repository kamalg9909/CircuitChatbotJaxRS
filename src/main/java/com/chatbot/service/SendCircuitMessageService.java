package com.chatbot.service;

import com.chatbot.models.CircuitSendMessageRequestObject;
import com.httpclients.SendMessageHttpClient;

public class SendCircuitMessageService {
	
	public void sendMessage(String conversation_id, String token, String contentMessage, String formMetaData) {

		// Setting Send Message Model Object
		CircuitSendMessageRequestObject messageDetails = new CircuitSendMessageRequestObject();

		String baseUrl = "https://circuitsandbox.net/rest/v2/conversations/" + conversation_id + "/messages/" + "item_id";

		messageDetails.setBaseURL(baseUrl);
		messageDetails.setToken(token);
		messageDetails.setContentMessage(contentMessage);
		messageDetails.setFormMetaData(formMetaData);

		// Sending Message
		SendMessageHttpClient sendMessage = new SendMessageHttpClient();
		sendMessage.postmessage(messageDetails);

	}

}
