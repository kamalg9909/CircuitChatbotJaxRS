package com.chatbot.service;

import com.chatbot.models.CircuitSendMessageRequestObject;
import com.chatbot.models.ConversationDetails;
import com.chatbot.models.Item;
import com.chatbot.utils.CircuitSendMessageReqBuilder;
import com.chatbot.utils.GetTicketInfoObject;
import com.chatbot.utils.ServiceNowRequestBuilder;
import com.httpclients.SendMessageHttpClient;
import com.httpclients.ServiceNowHttpClient;
import com.servicenow.models.SnowHTTPOutput;
import com.servicenow.models.SnowHTTPRequestObject;
import com.servicenow.models.SnowTicketInfoOBJ;

public class AddConversationRequestService {
	SnowTicketInfoOBJ ticketInfoOBJ;

	public void processRequest(ConversationDetails conversationDetails, String javaAppCreatorId) {
		System.out.println("\n\n" + "Inside AddConversationRequestService - processRequest()");
		
		//Content will have the User typed text and will help is finding the action to create a form and send reply. 
		String action = conversationDetails.getItem().getText().getContent().toLowerCase();

		// Parent Id is nothing but Circuit ItemID will help in getting the ItemObject.
		String parentID = conversationDetails.getItem().getParentId();

		// check if Parent Id is null or not
		if (parentID != null) {
			System.out.println("Parent ID is : " + parentID);

			// Getting Circuit Item Object via http
			Item circuitItemObject = new GetTicketInfoObject().gettingParentMessageItem(parentID);

			// Checking if the Creater of the parent message is us or some user
			// IF same then getting the SnowTicketInfoOBJ
			if (circuitItemObject.getCreatorId().equals(javaAppCreatorId)) {
				System.out.println("Parent Message Item is created by Our Snow Webhook");
				ticketInfoOBJ = new GetTicketInfoObject().getTicketInfoObjectFromItemObject(circuitItemObject);
				System.out.println("Setting parentID in SnowTicketInfoObject to set in our Form.");
				ticketInfoOBJ.setItem_id(parentID);
				
				//Getting SNOW Ticket Object :  HTTP Call  & Checking if the Ticket is available in Snow or deleted.
				SnowHTTPRequestObject requestObject = new ServiceNowRequestBuilder().getTicketInfoRequestBuilder(ticketInfoOBJ);
				SnowHTTPOutput httpOutput = new ServiceNowHttpClient().getTicketDetails(requestObject);
				System.out.println("Request Status: " + httpOutput.getHttpresponseStatus());
				
				//Checking if Http Call Got any error
				if(httpOutput.getHttpresponseStatus().contains("error")) {
					sendResponseToCircuitUser(ticketInfoOBJ, httpOutput);
					return;
				}
				
				//Checking if the current Message Ticket is already closed or not.
				//if closed thn send a reply back to circuit user : "Ticket already closed. Cant do any further action.
				if(httpOutput.getResult().getState().equalsIgnoreCase("closed")) {
					httpOutput.setHttpresponseStatus("error");
					httpOutput.setStatusString("Ticket is already been closed. You cant perform any more Operations");
					sendResponseToCircuitUser(ticketInfoOBJ, httpOutput);
					return;
				}
				
				if(action.toLowerCase().contains("create")) {
					httpOutput.setHttpresponseStatus("error");
					httpOutput.setStatusString("You Cant Perform this Operation. Please start a new conversation");
					sendResponseToCircuitUser(ticketInfoOBJ, httpOutput);
				}
			}else {
				System.out.println("This is not a New Chat and  Circuit User added a new item in a chat. ");
				System.out.println("Parent Message Item is created by User..");
				
				SnowHTTPOutput httpOutput = new SnowHTTPOutput();
				httpOutput.setHttpresponseStatus("info");
				httpOutput.setStatusString("This functionality is under development....");
				
				ticketInfoOBJ = new SnowTicketInfoOBJ();
				ticketInfoOBJ.setU_convid(conversationDetails.getItem().getConvId());
				ticketInfoOBJ.setItem_id(parentID);
				
				sendResponseToCircuitUser(ticketInfoOBJ, httpOutput);
				
				return;
			}	
		
		} else {
			//Parent ID is Null means its a new message.
			System.out.println("Its a new Conversation started by Circuit User");
			System.out.println("Parent ID is : " + parentID);
			
			System.out.println("Creating a new SnowTicketInfoObject for our New Form");
			ticketInfoOBJ = new SnowTicketInfoOBJ();
			ticketInfoOBJ.setU_convid(conversationDetails.getItem().getConvId());
			ticketInfoOBJ.setItem_id(conversationDetails.getItem().getItemId());
		}

		CircuitSendMessageRequestObject messageDetails = new CircuitSendMessageReqBuilder().sendActionFormToCircuitUser(ticketInfoOBJ, action);

		// Sending Message : Call Circuit Client to send the message
		SendMessageHttpClient sendMessage = new SendMessageHttpClient();
		sendMessage.postmessage(messageDetails);

	}
	
	private void sendResponseToCircuitUser(SnowTicketInfoOBJ ticketInfoObj, SnowHTTPOutput httpOutput) {

		CircuitSendMessageRequestObject messageDetails = new CircuitSendMessageReqBuilder()
				.sendResponseToCircuitUser(ticketInfoObj, httpOutput);

		System.out.println("messageDetails : " + messageDetails);

		//Sending Message : Call Circuit Client to send the message
		SendMessageHttpClient sendMessage = new SendMessageHttpClient();
		sendMessage.postmessage(messageDetails);
	}

}
