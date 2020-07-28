package com.circuit.webhooks;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.chatbot.service.SnowTriggerRequestService;
import com.servicenow.models.SnowTicketInfoOBJ;

@Path("ticketnotification")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServicenowWebhook {

	@POST
	public SnowTicketInfoOBJ processTicketNotification(SnowTicketInfoOBJ ticketInfoOBJ) {

		System.out.println("SnowIncidentNotification Class :  processTicketNotification");
		System.out.println(ticketInfoOBJ);

		SnowTriggerRequestService processingRequest = new
		SnowTriggerRequestService();
		 processingRequest.processRequest(ticketInfoOBJ);

		return ticketInfoOBJ;
	}

}

//Class Info

//This is RestAPI Webhook that deals with Servicenow Request whenever a p1 or p2 gets created
//Will get 4 parameters : Number, Table Name, Instance Name, Circuit Conversation ID
//First get the ticket details via Snow Table api
//Message the ticket Details on Circuit + "Email ID" : TextBox and "Assign To Me" button
//Process the response from circuit to update the assigned to field in Ticket in Snow
//Send back the Success Message to User.
