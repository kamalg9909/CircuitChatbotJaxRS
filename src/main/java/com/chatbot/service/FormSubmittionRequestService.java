package com.chatbot.service;

import java.util.List;

import com.chatbot.models.CircuitSendMessageRequestObject;
import com.chatbot.models.ConversationDetails;
import com.chatbot.models.Data;
import com.chatbot.models.RequestFormEnums;
import com.chatbot.utils.CircuitSendMessageReqBuilder;
import com.chatbot.utils.GetCircuitUserEmailID;
import com.chatbot.utils.ServiceNowRequestBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.httpclients.SendMessageHttpClient;
import com.httpclients.ServiceNowHttpClient;
import com.servicenow.models.ServicenowTicketObject;
import com.servicenow.models.SnowHTTPOutput;
import com.servicenow.models.SnowHTTPRequestObject;
import com.servicenow.models.SnowTicketInfoOBJ;

public class FormSubmittionRequestService {

	public void processRequest(ConversationDetails submittedFormDetails) {
		System.out.println("Inside ProcessingFormSubmitRequest Class : processRequest()");

		String formId = submittedFormDetails.getSubmitFormData().getFormId();

		System.out.println("formId : " + formId);

		String[] bits = formId.split("&");

		String circuitFormName = bits[0];
		System.out.println("circuitFormName : " + circuitFormName);

		String ticketInfojsonString = bits[1];
		System.out.println("SnowInfojsonString : " + ticketInfojsonString);

		System.out.println("Converting Json String to Ticket Info Object");
		SnowTicketInfoOBJ ticketInfoOBJ = getandset_TicketInfoObject(ticketInfojsonString,submittedFormDetails);
		System.out.println(ticketInfoOBJ);

		// Transferring the Circuit Form Data into ServiceNow ticket Object which will
		// be common for all SNOW Post requests.
		System.out.println("Converting Circuit Form Data to Ticket Object for Snow Operations.");
		ServicenowTicketObject ticketOBJ = formDataToSnowticketObj(submittedFormDetails);

		if (circuitFormName.contains(RequestFormEnums.AssignedToForm.toString())) {
			System.out.println("Inside AssignedToForm If");

			// Getting the Email Id filled by the user.
			String assignedtoEmail = ticketOBJ.getAssigned_to();

			// If Form's Email Textbox is empty, Get the EmailID of the circuit user who
			// submitted the form.
			if (assignedtoEmail == "") {
				System.out.println("Assigned To - Email is Empty");

				// getting Circuit User Email who submitted the form
				assignedtoEmail = new GetCircuitUserEmailID().getEmailID(submittedFormDetails);
				ticketOBJ.setAssigned_to(assignedtoEmail);
			}

			// Verifying EmailId in Service now if user with email is available in snow
			SnowHTTPOutput httpOutput = verifySnowUser(ticketInfoOBJ, assignedtoEmail);
			if (httpOutput.getHttpresponseStatus().contains("error")) {
				sendResponseToCircuitUser(ticketInfoOBJ, httpOutput);
				return;
			}

			// updating ticketing in snow
			SnowHTTPOutput httpSnowOutput = updateSnowTicket(ticketInfoOBJ, ticketOBJ);

			// sending Servicenow output to circuit.
			sendResponseToCircuitUser(ticketInfoOBJ, httpSnowOutput);
		}

		else if (circuitFormName.contains(RequestFormEnums.CreateTicketForm.toString())) {
			// creating ticketing in snow
			SnowHTTPOutput httpSnowOutput = createSnowTicket(ticketInfoOBJ, ticketOBJ);

			// sending Servicenow output to circuit.
			sendResponseToCircuitUser(ticketInfoOBJ, httpSnowOutput);

		} else {
			SnowHTTPOutput httpSnowOutput = updateSnowTicket(ticketInfoOBJ, ticketOBJ);

			// sending Servicenow output to circuit.
			sendResponseToCircuitUser(ticketInfoOBJ, httpSnowOutput);
		}

	}
	

	private SnowHTTPOutput updateSnowTicket(SnowTicketInfoOBJ ticketInfoOBJ, ServicenowTicketObject ticketOBJ) {
		SnowHTTPRequestObject requestObject = new ServiceNowRequestBuilder().updateTicketRequestBuilder(ticketInfoOBJ,
				ticketOBJ);

		SnowHTTPOutput httpSnowOutput = new ServiceNowHttpClient().updateTicket(requestObject);
		System.out.println("Request Status: " + httpSnowOutput.getHttpresponseStatus());
		System.out.println("Result : " + httpSnowOutput.getResult());

		return httpSnowOutput;

	}

	private SnowHTTPOutput createSnowTicket(SnowTicketInfoOBJ ticketInfoOBJ, ServicenowTicketObject ticketOBJ) {

		SnowHTTPRequestObject requestObject = new ServiceNowRequestBuilder().createTicketRequestBuilder(ticketInfoOBJ,
				ticketOBJ);

		SnowHTTPOutput httpSnowOutput = new ServiceNowHttpClient().createTicket(requestObject);
		System.out.println("Request Status: " + httpSnowOutput.getHttpresponseStatus());
		System.out.println("Result : " + httpSnowOutput.getResult());
		// sendResponseToCircuitUser(ticketInfoOBJ, httpSnowOutput);

		return httpSnowOutput;

	}
	
	private SnowTicketInfoOBJ getandset_TicketInfoObject(String ticketInfojsonString, ConversationDetails submittedFormDetails) {

		//getting SnowTicketInfoOBJ from the formid Json String... 
		SnowTicketInfoOBJ ticketInfoOBJ = new SnowTicketInfoOBJ();
		ObjectMapper mapper = new ObjectMapper();

		try {
			ticketInfoOBJ = mapper.readValue(ticketInfojsonString, SnowTicketInfoOBJ.class);
		} catch (JsonProcessingException e) {
			System.out.println("Error While parsing the json to object.");
			e.printStackTrace();
		}
		
		
		//Setting SnowTicketInfoOBJ 
		
		// Setting ItemID to which we have to send reply to circuit user after
		// processing a snow request .
		//if new means its a new conversation and there is no parent id. hence this the id that we have to reply to. 
		if (ticketInfoOBJ.getItem_id().equalsIgnoreCase("new")) {
			System.out.println("Getting Item ID from Circuit Form....................");
			ticketInfoOBJ.setItem_id(submittedFormDetails.getSubmitFormData().getItemId());
		}
		
		// If the form is Snow Triggered form thn snow instance name and snow table name will be available in SnowTicketInfoOBJ
		// Else user will provide it in the form and get it from the submitted form data. 
		//Checking if Snow Instance name and Table name is coming from 
		if(ticketInfoOBJ.getInstance_name() == "") {
			ServicenowTicketObject ticketObject = new ServicenowTicketObject();
			List<Data> dataList = submittedFormDetails.getSubmitFormData().getData();
			for (Data data : dataList) {
				if (data.getName().equalsIgnoreCase("instance_name")) {
					ticketInfoOBJ.setInstance_name(data.getValue());
				}
			}
		}
		
		// hard coding the table name here... 
		ticketInfoOBJ.setTable_name("incident");
		return ticketInfoOBJ;

	}

	private ServicenowTicketObject formDataToSnowticketObj(ConversationDetails submittedFormDetails) {

		ServicenowTicketObject ticketObject = new ServicenowTicketObject();
		List<Data> dataList = submittedFormDetails.getSubmitFormData().getData();
		for (Data data : dataList) {
			
			if (data.getName().equalsIgnoreCase("category")) {
				ticketObject.setCategory(data.getValue());
			}
			
			if (data.getName().equalsIgnoreCase("description")) {
				ticketObject.setDescription(data.getValue());
			}
			
			if (data.getName().equalsIgnoreCase("short_description")) {
				ticketObject.setShort_description(data.getValue());
			}
			
			if (data.getName().equalsIgnoreCase("assignment_group")) {
				ticketObject.setAssignment_group(data.getValue());
			}
			
			if (data.getName().equalsIgnoreCase("assigned_to")) {
				ticketObject.setAssigned_to(data.getValue());
			}

			if (data.getName().equalsIgnoreCase("state")) {
				ticketObject.setState(data.getValue());
			}

			if (data.getName().equalsIgnoreCase("priority")) {
				ticketObject.setPriority(data.getValue());
			}

			if (data.getName().equalsIgnoreCase("close_code")) {
				ticketObject.setClose_code(data.getValue());
			}

			if (data.getName().equalsIgnoreCase("close_notes")) {
				ticketObject.setClose_notes(data.getValue());
			}

			if (data.getName().equalsIgnoreCase("work_notes")) {
				ticketObject.setWork_notes(data.getValue());
			}
			if (data.getName().equalsIgnoreCase("hold_reason")) {
				ticketObject.setHold_reason(data.getValue());
			}
		}

		return ticketObject;

	}

	private SnowHTTPOutput verifySnowUser(SnowTicketInfoOBJ ticketInfoObj, String userEmail) {

		SnowHTTPRequestObject requestObject = new ServiceNowRequestBuilder().verifySnowUserRequestBuilder(ticketInfoObj,
				userEmail);

		SnowHTTPOutput httpOutput = new ServiceNowHttpClient().verifySNOWUserViaEmail(requestObject);
		System.out.println("Request Status: " + httpOutput.getHttpresponseStatus());

		return httpOutput;
	}

	private void sendResponseToCircuitUser(SnowTicketInfoOBJ ticketInfoObj, SnowHTTPOutput httpOutput) {

		CircuitSendMessageRequestObject messageDetails = new CircuitSendMessageReqBuilder()
				.sendResponseToCircuitUser(ticketInfoObj, httpOutput);

		System.out.println("messageDetails : " + messageDetails);

		// Sending Message : Call Circuit Client to send the message
		SendMessageHttpClient sendMessage = new SendMessageHttpClient();
		sendMessage.postmessage(messageDetails);
	}

}