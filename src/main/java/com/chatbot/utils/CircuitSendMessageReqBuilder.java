package com.chatbot.utils;

import com.chatbot.models.CircuitSendMessageRequestObject;
import com.httpclients.GetCircuitTokenHttpClient;
import com.servicenow.models.SnowHTTPOutput;
import com.servicenow.models.SnowTicketInfoOBJ;

public class CircuitSendMessageReqBuilder {

	CircuitSendMessageRequestObject messageDetails = new CircuitSendMessageRequestObject();
	CircuitFormJsonBuilder circuitFormJsonString = new CircuitFormJsonBuilder();

	public CircuitSendMessageRequestObject snowWebhookReq(SnowTicketInfoOBJ ticketInfoOBJ, SnowHTTPOutput httpOutput) {

		System.out.println("Inside CircuitSendMessageReqBuilder Class :  snowWebhookReq()");

		// Get the Circuit Send Message API Url
		String sendMessageHttpUrl = new CircuitRequestURLBuilder().getBaseUrlToSendMessage(ticketInfoOBJ);
		messageDetails.setBaseURL(sendMessageHttpUrl);

		// Getting Circuit Access Token
		messageDetails.setToken(getCircuitToken());

		// Set Subject
		messageDetails.setSubject("ServiceNow - New Ticket Details - " + ticketInfoOBJ.getNumber());
		messageDetails.setContentMessage("");

		// Get Form Metadata.
		String formMetadata = circuitFormJsonString.getCircuitAssignedToForm(ticketInfoOBJ, httpOutput.getResult());
		messageDetails.setFormMetaData(formMetadata);
		System.out.println(messageDetails);

		return messageDetails;
	}

	public CircuitSendMessageRequestObject sendResponseToCircuitUser(SnowTicketInfoOBJ ticketInfoOBJ,
			SnowHTTPOutput httpoutput) {

		System.out.println("Inside CircuitSendMessageReqBuilder Class :  sendResponseToCircuitUser()");

		// Get the Circuit Send Message API Url
		String sendMessageHttpUrl = new CircuitRequestURLBuilder().getBaseUrlToSendMessage(ticketInfoOBJ);
		messageDetails.setBaseURL(sendMessageHttpUrl);

		// Getting Circuit Access Token and setting it
		messageDetails.setToken(getCircuitToken());

		if (httpoutput.getHttpresponseStatus().contains("success")) {
			System.out.println("Inside Success IF");
			messageDetails.setContentMessage("&#9989;" + " " + httpoutput.getStatusString());

			String formMetadata = circuitFormJsonString.sendSuccessMessageToCircuit(httpoutput.getResult());
			messageDetails.setFormMetaData(formMetadata);
			System.out.println("formMetadata :- " + formMetadata);

		} if (httpoutput.getHttpresponseStatus().contains("error")) {
			messageDetails.setContentMessage("&#10060;" + " " + httpoutput.getStatusString());
			messageDetails.setFormMetaData("");
			// System.out.println("formMetadata :- " + formMetadata);
			
		} if (httpoutput.getHttpresponseStatus().contains("info")) {
			messageDetails.setContentMessage("&#8505;" + " " + httpoutput.getStatusString());
			messageDetails.setFormMetaData("");
			// System.out.println("formMetadata :- " + formMetadata);
		}
		
		return messageDetails;
	}

	public CircuitSendMessageRequestObject sendActionFormToCircuitUser(SnowTicketInfoOBJ ticketInfoOBJ, String action) {

		System.out.println("Inside CircuitSendMessageReqBuilder Class :  sendResponseFormToCircuitUser()");

		// Get the Circuit Send Message API Url
		String sendMessageHttpUrl = new CircuitRequestURLBuilder().getBaseUrlToSendMessage(ticketInfoOBJ);
		messageDetails.setBaseURL(sendMessageHttpUrl);

		// Getting Circuit Access Token and setting it
		messageDetails.setToken(getCircuitToken());
		
		// Set Subject
		messageDetails.setContentMessage("&#128203;" + " ServiceNow");

		String formMetadata = circuitFormJsonString.getActionsForm(ticketInfoOBJ, action);
		messageDetails.setFormMetaData(formMetadata);
		System.out.println("formMetadata :- " + formMetadata);

		return messageDetails;
	}

	private String getCircuitToken() {

		String circuitToken = new GetCircuitTokenHttpClient().getAccessToken();
		System.out.println("circuitToken : " + circuitToken);

		return circuitToken;
	}

}
