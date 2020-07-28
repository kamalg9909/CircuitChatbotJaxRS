package com.chatbot.service;

import com.chatbot.models.CircuitSendMessageRequestObject;
import com.chatbot.utils.CircuitSendMessageReqBuilder;
import com.chatbot.utils.ServiceNowRequestBuilder;
import com.httpclients.SendMessageHttpClient;
import com.httpclients.ServiceNowHttpClient;
import com.servicenow.models.SnowHTTPOutput;
import com.servicenow.models.SnowHTTPRequestObject;
import com.servicenow.models.SnowTicketInfoOBJ;

public class SnowTriggerRequestService {
	
	CircuitSendMessageRequestObject messageDetails;

	public void processRequest(SnowTicketInfoOBJ ticketInfoOBJ) {
		System.out.println("Inside ProcessingServicenowRequest Class : processRequest()");

		System.out.println("Item ID : " + ticketInfoOBJ.getItem_id());


		// Getting Ticket Details from Snow Incident table via ticketInfoObj's number
		// var.
		// Getting the Snow HTTP Request Object to call the ServicNow Table API to get
		// the Ticket Information.
		SnowHTTPRequestObject requestObject = new ServiceNowRequestBuilder().getTicketInfoRequestBuilder(ticketInfoOBJ);

		SnowHTTPOutput httpOutput = new ServiceNowHttpClient().getTicketDetails(requestObject);
		System.out.println("Request Status: " + httpOutput.getHttpresponseStatus());

		if (httpOutput.getHttpresponseStatus().toLowerCase().contains("success")) {
			System.out.println("Request Successfull");
			// Process : Send Message on Circuit and Notify the Circuit Group abt this new
			// ticket creation
			// Create a CircuitRequestBuilder to send the notification on Circuit.
			 messageDetails = new CircuitSendMessageReqBuilder()
					.snowWebhookReq(ticketInfoOBJ, httpOutput);

			// Sending Message : Call Circuit Client to send the message
			SendMessageHttpClient sendMessage = new SendMessageHttpClient();
			sendMessage.postmessage(messageDetails);
		}
	}
}
