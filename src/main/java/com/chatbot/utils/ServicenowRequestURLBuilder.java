package com.chatbot.utils;

import com.servicenow.models.SnowTicketInfoOBJ;

public class ServicenowRequestURLBuilder {

	String sysparm_fields = "sysparm_fields=number,category,short_description,assignment_group,state,priority,description,sys_id,assigned_to";
	String sysparm_display_value = "sysparm_display_value=true";

	public String snowGETRequestURL(SnowTicketInfoOBJ ticketInfoOBJ) {

		System.out.println("Inside createServiceNowGETRequestURL");
		String sysparm_query = "sysparm_query=number=" + ticketInfoOBJ.getNumber();

		String baseUrl = "https://" + ticketInfoOBJ.getInstance_name() + ".service-now.com/api/now/table/"
				+ ticketInfoOBJ.getTable_name() + "?" + sysparm_query + "&" + sysparm_fields + "&" + sysparm_display_value;

		System.out.println("ServiceNow Base Url : " + baseUrl);
		return baseUrl;
	}

	public String verifyUserViaEmailIDRequestURL(SnowTicketInfoOBJ ticketInfoOBJ, String emailId) {

		System.out.println("Inside verifyUserViaEmailIDRequestURL");
		String sysparm_query = "sysparm_query=email=" + emailId;
		String sysparm_fields = "sysparm_fields=name,gender,email";
		String baseUrl = "https://" + ticketInfoOBJ.getInstance_name() + ".service-now.com/api/now/table/sys_user" + "?"
				+ sysparm_query + "&" + sysparm_fields;

		System.out.println("ServiceNow Base Url to verify user : " + baseUrl);
		return baseUrl;
	}

	public String snowUpdateTicketRequestURL(SnowTicketInfoOBJ ticketInfoOBJ) {
		System.out.println("Inside snowUpdateTicketRequestURL");

		// https://{instance_url}/api/now/table/{table_name}/{sys_id}
		String baseUrl = "https://" + ticketInfoOBJ.getInstance_name() + ".service-now.com/api/now/table/"
				+ ticketInfoOBJ.getTable_name() + "/" + ticketInfoOBJ.getSys_id() + "?" + sysparm_fields + "&"
				+ sysparm_display_value;
		System.out.println("ServiceNow Base Url : " + baseUrl);
		return baseUrl;
	}

	public String snowCreateTicketRequestURL(SnowTicketInfoOBJ ticketInfoOBJ) {
		System.out.println("Inside snowUpdateTicketRequestURL");

		String baseUrl = "https://" + ticketInfoOBJ.getInstance_name() + ".service-now.com/api/now/table/" + ticketInfoOBJ.getTable_name() + "?" + sysparm_fields
				+ "&" + sysparm_display_value;
		System.out.println("ServiceNow Base Url : " + baseUrl);
		return baseUrl;
	}
}
