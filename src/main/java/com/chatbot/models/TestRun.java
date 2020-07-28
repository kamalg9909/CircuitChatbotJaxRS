package com.chatbot.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicenow.models.SnowTicketInfoOBJ;

public class TestRun {

	

	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
	
		String json = "{\r\n" + 
				"    \"type\": \"TEXT\",\r\n" + 
				"    \"itemId\": \"6ac9e73a-2101-4e95-aadf-22e9b03a9ade\",\r\n" + 
				"    \"convId\": \"34fb495b-04c8-45db-a533-4e0c60fb27e5\",\r\n" + 
				"    \"text\": {\r\n" + 
				"        \"state\": \"CREATED\",\r\n" + 
				"        \"subject\": \"Service Now - New Ticket Details - INC0010063\",\r\n" + 
				"        \"content\": \"\",\r\n" + 
				"        \"formMetaData\": \"{\\\"id\\\":\\\"AssignedToForm&{\\\\\\\"number\\\\\\\":\\\\\\\"INC0010063\\\\\\\",\\\\\\\"instance_name\\\\\\\":\\\\\\\"dev90475\\\\\\\",\\\\\\\"table_name\\\\\\\":\\\\\\\"incident\\\\\\\",\\\\\\\"u_convid\\\\\\\":\\\\\\\"34fb495b-04c8-45db-a533-4e0c60fb27e5\\\\\\\",\\\\\\\"sys_id\\\\\\\":\\\\\\\"135619ed0711501066e8f0ef7c1ed0b5\\\\\\\"}\\\",\\\"title\\\":\\\"Assign Ticket Form\\\",\\\"controls\\\":[{\\\"type\\\":\\\"LABEL\\\",\\\"text\\\":\\\"<b>Number  : INC0010063\\\\nCategory  : Inquiry / Help\\\\nCompany  : null\\\\nPriority  : 1 - Critical\\\\nShort Description  : I can't get my weather report\\\\nAssignment Group  : Service Desk\\\\nDescription  : WeatherBug icon has disappeared from my desktop. Unable to get my weather report.\\\\nState  : In Progress\\\\nAssigned To  : Kamal Garg</b>\\\"},{\\\"type\\\":\\\"INPUT\\\",\\\"name\\\":\\\"assigned_to\\\",\\\"title\\\":\\\"Assigned To\\\",\\\"text\\\":\\\"Enter Email ID (Optional)\\\",\\\"size\\\":\\\"small\\\"},{\\\"type\\\":\\\"BUTTON\\\",\\\"text\\\":\\\"Assign Ticket\\\"}]}\",\r\n" + 
				"        \"isWebhookMessage\": false\r\n" + 
				"    },\r\n" + 
				"    \"creationTime\": 1591793244806,\r\n" + 
				"    \"modificationTime\": 1591793244806,\r\n" + 
				"    \"creatorId\": \"2e618c91-f9d9-4719-a44f-5344ff9e4488\",\r\n" + 
				"    \"includeInUnreadCount\": true\r\n" + 
				"}";
		ObjectMapper objectMapper = new ObjectMapper();
		Item circuitItem = objectMapper.readValue(json, Item.class);
		System.out.println(circuitItem);
		
		System.out.println("FormMetaData: "+ circuitItem.getText().getFormMetaData());
		FormMetaData metadata = objectMapper.readValue(circuitItem.getText().getFormMetaData(), FormMetaData.class);
		System.out.println(metadata);
		
		
		String[] bits = metadata.getId().split("&");

		String circuitFormName = bits[0];
		System.out.println("circuitFormName : " + circuitFormName);

		String ticketInfojsonString = bits[1];
		System.out.println("SnowInfojsonString : " + ticketInfojsonString);

		System.out.println("Converting Json String to Ticket Info Object");
		SnowTicketInfoOBJ ticketInfoObject = jsonToObject(ticketInfojsonString);
		System.out.println("************************************************");
		System.out.println("SnowTicketInfoOBJ : " + ticketInfoObject);
		System.out.println("************************************************");
		
	}

	
	private static SnowTicketInfoOBJ jsonToObject(String ticketInfojsonString) {

		SnowTicketInfoOBJ ticketInfoObj = new SnowTicketInfoOBJ();
		ObjectMapper mapper = new ObjectMapper();

		try {
			ticketInfoObj = mapper.readValue(ticketInfojsonString, SnowTicketInfoOBJ.class);
		} catch (JsonProcessingException e) {
			System.out.println("Error While parsing the json to object.");
			e.printStackTrace();
		}
		return ticketInfoObj;

	}

	
}
