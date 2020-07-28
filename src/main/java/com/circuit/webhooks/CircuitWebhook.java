package com.circuit.webhooks;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.chatbot.models.ConversationDetails;
import com.chatbot.service.AddConversationRequestService;
import com.chatbot.service.FormSubmittionRequestService;

@Path("circuit")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CircuitWebhook {

	// final static Logger logger = Logger.getLogger(StartConversation.class);

	// This Id is our id as message creater.
	String javaAppCreatorId = "efd61bfd-499a-4c7e-9f75-67999b49486c";

	@GET
	public String testMethod() {
		return "It Works";
	}

	@POST
	public ConversationDetails circuitWebhook(ConversationDetails conversationDetails) {
		// logger.info("Inside Start Conversation Webhook");

		if (conversationDetails.getType().equalsIgnoreCase("USER.SUBMIT_FORM_DATA")) {

			System.out.println("CircuitWebhook Class : USER.SUBMIT_FORM_DATA");
			System.out.println(conversationDetails.toString());

			new FormSubmittionRequestService().processRequest(conversationDetails);

		} else if (conversationDetails.getType().equalsIgnoreCase("CONVERSATION.CREATE")) {
			System.out.println("Doing Nothing");
			return conversationDetails;

		} else {
			
			if (conversationDetails.getItem().getCreatorId().equals(javaAppCreatorId)) {
				System.out.println("Doing Nothing cos the messsage is created by Bot....");
				return conversationDetails; // Do nothing coz webhook was triggered due to our message....
			}

			System.out.println("CircuitWebhook Class :  CONVERSATION.ADD_ITEM");
			System.out.println(conversationDetails);

			AddConversationRequestService processingRequest = new AddConversationRequestService();
			processingRequest.processRequest(conversationDetails, javaAppCreatorId);
		}

		return conversationDetails;
	}
}
