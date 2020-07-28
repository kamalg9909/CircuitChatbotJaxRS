package com.chatbot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicenow.models.ServicenowTicketObject;
import com.servicenow.models.SnowHTTPRequestObject;
import com.servicenow.models.SnowTicketInfoOBJ;

public class ServiceNowRequestBuilder {

	SnowHTTPRequestObject requestObject = new SnowHTTPRequestObject();

	public SnowHTTPRequestObject getTicketInfoRequestBuilder(SnowTicketInfoOBJ ticketInfoOBJ) {

		// Building Servicenow Request BaseUrl to Ticket Record via Ticket Number.

		String httpBaseUrl = new ServicenowRequestURLBuilder().snowGETRequestURL(ticketInfoOBJ);
		requestObject.setBaseUrl(httpBaseUrl);

		return requestObject;

	}
	
	public SnowHTTPRequestObject verifySnowUserRequestBuilder(SnowTicketInfoOBJ ticketInfoOBJ,
			String email) {

		String httpBaseUrl = new ServicenowRequestURLBuilder().verifyUserViaEmailIDRequestURL(ticketInfoOBJ, email);
		requestObject.setBaseUrl(httpBaseUrl);
		return requestObject;
	}


	public SnowHTTPRequestObject updateTicketRequestBuilder(SnowTicketInfoOBJ ticketInfoOBJ,
			ServicenowTicketObject ticketOBJ) {

		// Building Servicenow Request BaseUrl to Ticket Record via Ticket Number.

		String httpBaseUrl = new ServicenowRequestURLBuilder().snowUpdateTicketRequestURL(ticketInfoOBJ);
		requestObject.setBaseUrl(httpBaseUrl);
		requestObject.setRequestBody(objectToJson(ticketOBJ));

		return requestObject;
	}
	
	public SnowHTTPRequestObject createTicketRequestBuilder(SnowTicketInfoOBJ ticketInfoOBJ,
			ServicenowTicketObject ticketOBJ) {

		// Building Servicenow Request BaseUrl to Ticket Record via Ticket Number.

		String httpBaseUrl = new ServicenowRequestURLBuilder().snowCreateTicketRequestURL(ticketInfoOBJ);
		requestObject.setBaseUrl(httpBaseUrl);
		requestObject.setRequestBody(objectToJson(ticketOBJ));

		return requestObject;
	}

	private String objectToJson(ServicenowTicketObject ticketOBJ) {
		ObjectMapper mapper = new ObjectMapper();
		String requestBody = "";

		try {
			requestBody = mapper.writeValueAsString(ticketOBJ);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(requestBody);
		return requestBody;
	}

}
