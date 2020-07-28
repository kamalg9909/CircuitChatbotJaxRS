package com.chatbot.utils;

import com.servicenow.models.SnowTicketInfoOBJ;

public class CircuitRequestURLBuilder {
	
	String baseUrl = "https://circuitsandbox.net/rest/v2/"; // + conversation_id + "/messages/" + "item_id";
	
	public String getBaseUrlToSendMessage(SnowTicketInfoOBJ ticketInfoOBJ) {
		System.out.println("Creating Circuit Http Client URL to send  Message ");
		
		String convid = ticketInfoOBJ.getU_convid();
		String itemid = ticketInfoOBJ.getItem_id();
		
		if(itemid.equalsIgnoreCase("new")) {
			baseUrl = baseUrl + "conversations/" + convid + "/messages"; 
		}else {
			baseUrl = baseUrl + "conversations/" + convid + "/messages/" +  itemid;
		}
		
		 return baseUrl;
	}
	
	public String getBaseUrlToGetCircuitItem(String parentID) {
		System.out.println("Creating Circuit Http Client URL to get Parent Item of the Current Message item ");
		return baseUrl + "conversations/" + "messages/" + parentID;
		
	}
	
	public String getBaseUrlToGetUserEmailID(String submitterId) {
		System.out.println("Creating Circuit Http Client URL to get the User Email id who submitted the Form ");
		return baseUrl + "users/" + submitterId;
		
	}
	
	
	
}
