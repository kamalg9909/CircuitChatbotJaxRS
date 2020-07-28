package com.chatbot.utils;

import com.chatbot.models.FormMetaData;
import com.chatbot.models.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.httpclients.CircuitGETHttpClient;
import com.httpclients.GetCircuitTokenHttpClient;
import com.servicenow.models.SnowTicketInfoOBJ;


//GetTicketInfoObject Class 
//	--> first get the Circuit Item.
//  -->  item.formetadata
public class GetTicketInfoObject {

	public Item gettingParentMessageItem(String parentID) {
		
		System.out.println("Getting Parent Item via Parent ID (Http Call)");

		String httpurl = new CircuitRequestURLBuilder().getBaseUrlToGetCircuitItem(parentID);

		// Getting Circuit Access Token
		String token = new GetCircuitTokenHttpClient().getAccessToken();
		
		Item circuitItem = new CircuitGETHttpClient().getCircuitParentItem(httpurl, token);
		
		System.out.println("");

		return circuitItem;
	}

	public SnowTicketInfoOBJ getTicketInfoObjectFromItemObject(Item circuitItemObject) {
		
		System.out.println("Getting TicketInfoObject From Parent Message Item Object");
		System.out.println("TicketInfoObject will be in the Item's FormMetaData as Json String...");
		System.out.println("FormMetaData: " + circuitItemObject.getText().getFormMetaData());

		System.out.println("Converting Form Metadata Json String to FormMetaData Class Object");
		FormMetaData formMetaData = new FormMetaData();
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			formMetaData = objectMapper.readValue(circuitItemObject.getText().getFormMetaData(), FormMetaData.class);
			System.out.println(formMetaData);
		} catch (JsonProcessingException e) {
			System.out.println("Error While parsing the json to object.");
			e.printStackTrace();
		}
		

		// Getting Form ID String and Splitting it on '&'. First element will be the
		// Form Name and Second element will the Json String of SnowTicketInfoOBJ
		
		String[] bits = formMetaData.getId().split("&");
		// String circuitFormName = bits[0];
		// System.out.println("circuitFormName : " + circuitFormName);

		String SnowTicketInfojsonString = bits[1];
		System.out.println("Ticket Indo Json String : " + SnowTicketInfojsonString);

		System.out.println("Converting Json String to Ticket Info Object.....");
		
		SnowTicketInfoOBJ ticketInfoObject = jsonToObject(SnowTicketInfojsonString);
		System.out.println(ticketInfoObject);

		return ticketInfoObject;
	}

	private SnowTicketInfoOBJ jsonToObject(String ticketInfojsonString) {

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
