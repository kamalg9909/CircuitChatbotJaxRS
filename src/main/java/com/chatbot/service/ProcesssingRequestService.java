package com.chatbot.service;

/*
public class ProcesssingRequestService {

	SendCircuitMessage sendCircuitMessage = new SendCircuitMessage();
	CircuitFormJsonBuilder jsonbuilder = new CircuitFormJsonBuilder();
	CircuitFormSubmitDetails formSubmitDetails;
	CircuitSendMessageRequestObject sendMessageRequestObejct;
	SnowBaseURLBuilder snowRequestURLBuilder;
	SnowHTTPRequestObject snowHttpRequestObject;
	String formMetaData = "";
	String conversation_id = "";
	String item_id = "";
	String token = "";
	String contentMessage = "";
	String tableName = "";
	String action = "";
	String formtype = "";
	String instance;


	public void processCircuitRequest(ConversationDetails details, String token) {
		System.out.println("Inside ProcesssingRequest Class : processCircuitRequest()");

		this.token = token;
		contentMessage = "Service Now";
		String type = "" + details.getType();
		System.out.println("Conversation TYPE : " + type);
		if (type.equalsIgnoreCase("CONVERSATION.ADD_ITEM")) {
			System.out.println("Inside CONVERSATION.ADD_ITEM if block");

			conversation_id = details.getItem().getConvId();
			item_id = details.getItem().getItemId();

			// Creating Circuit Service Option Form
			// creatingServiceFormRequest(details);

			// Sending to user
			sendCircuitMessage.sendMessage(type, type, type, type);

		} else if (type.equalsIgnoreCase("USER.SUBMIT_FORM_DATA")) {
			System.out.println("Inside USER.SUBMIT_FORM_DATA if block");

			String formId = details.getSubmitFormData().getFormId();
			System.out.println("formId : " + formId);

			String[] bits = formId.split("_");

			conversation_id = bits[bits.length - 2];
			item_id = bits[bits.length - 1];

			formtype = bits[0];
			System.out.println("formtype : " + formtype);

			if (formtype.equalsIgnoreCase("ServiceOptionForm")) {
				creatingActionFormRequest(details);
				sendCircuitMessage.sendMessage(type, type, type, type);
			} else {
				System.out.println("Inside serviceform Else");
				action = bits[0];
				tableName = bits[1];
				System.out.println("Its Action Form and we need to call the servicenow ");

				// Creating Snow Action Request and Calling Servicenow API to perform action and
				// getting the output back.
				creatingSnowActionRequest(details, action, tableName);

				System.out.println("Content Message : " + contentMessage);

				// Send the output back to User.
				sendCircuitMessage.sendMessage(type, type, type, type);
			}

			System.out.println("Action : " + action);
			System.out.println("TableName : " + tableName);

		}

		System.out.println("conversation_id : " + conversation_id);
		System.out.println("item_id : " + item_id);

	}

	public void creatingServiceFormRequest(ConversationDetails details) {

		formMetaData = jsonbuilder.getServiceOptionsForm(details);
		System.out.println("formMetaData Post Message - \n" + formMetaData);
	}

	public void creatingActionFormRequest(ConversationDetails details) {
		System.out.println("Inside creatingActionFormRequest()");
		List<Data> dataList = details.getSubmitFormData().getData();
		for (Data data : dataList) {
			if (data.getName().equalsIgnoreCase("servicenow_table")) {
				tableName = data.getValue();
			}
			if (data.getName().equalsIgnoreCase("servicenow_action")) {
				action = data.getValue();
			}
		}

		formMetaData = jsonbuilder.getServiceActionsForm(details, tableName, action, conversation_id, item_id);
		System.out.println("formMetaData Post Message - \n" + formMetaData);
	}

	public void creatingSnowActionRequest(ConversationDetails details, String action, String tableName) {

		System.out.println("Inside creatingSnowActionRequest()");
		formSubmitDetails = new CircuitFormSubmitDetails();

		if (action.equalsIgnoreCase("getticket")) {
			System.out.println("Inside getticket If");
			List<Data> dataList = details.getSubmitFormData().getData();
			for (Data data : dataList) {
				if (data.getName().equalsIgnoreCase("instance")) {
					instance = data.getValue();
				}
				if (data.getName().equalsIgnoreCase("number")) {
					formSubmitDetails.setNumber(data.getValue());
				}
			}

			snowRequestURLBuilder = new SnowBaseURLBuilder();
			String baseURL = snowRequestURLBuilder.snowGETRequestURL(formSubmitDetails, tableName, instance);

			snowHttpRequestObject = new SnowHTTPRequestObject();
			snowHttpRequestObject.setBaseUrl(baseURL);
			snowHttpRequestObject.setUsername("admin");
			snowHttpRequestObject.setPass("Atos@123");

			ServiceNowHttpClient snowHttpClient = new ServiceNowHttpClient();
			String output = snowHttpClient.getTicketDetails(snowHttpRequestObject);
			System.out.println("output : " + output);

			if (!output.contains("Try again")) {
				contentMessage = "Your Ticket Details as follows : ";
			}
			formMetaData = jsonbuilder.getCircuitFormLabal(output);
		}

		if (action.equalsIgnoreCase("createticket")) {
			System.out.println("Inside createticket If");
			List<Data> dataList = details.getSubmitFormData().getData();
			for (Data data : dataList) {
				if (data.getName().equalsIgnoreCase("instance")) {
					instance = data.getValue();
				}
				if (data.getName().equalsIgnoreCase("short_description")) {
					formSubmitDetails.setShort_description(data.getValue());
				}
				if (data.getName().equalsIgnoreCase("category")) {
					formSubmitDetails.setCategory(data.getValue());
				}
				if (data.getName().equalsIgnoreCase("description")) {
					formSubmitDetails.setDescription(data.getValue());
				}
				if (data.getName().equalsIgnoreCase("impact")) {
					formSubmitDetails.setImpact(data.getValue());
				}
				if (data.getName().equalsIgnoreCase("assignment_group")) {
					formSubmitDetails.setAssignment_group(data.getValue());
				}
			}

			snowRequestURLBuilder = new SnowBaseURLBuilder();
			String baseURL = snowRequestURLBuilder.snowPostRequestURL(formSubmitDetails, tableName, instance);

			snowHttpRequestObject = new SnowHTTPRequestObject();
			snowHttpRequestObject.setBaseUrl(baseURL);
			snowHttpRequestObject.setUsername("admin");
			snowHttpRequestObject.setPass("Atos@123");

			String postBody = javaObjectToJson(formSubmitDetails);
			snowHttpRequestObject.setPostBody(postBody);

			ServiceNowHttpClient snowHttpClient = new ServiceNowHttpClient();

			String output = snowHttpClient.postTicketDetails(snowHttpRequestObject);
			System.out.println("output : " + output);

			if (!output.contains("Try again")) {
				contentMessage = "Ticket has been created sucessfully :";
			}
			formMetaData = jsonbuilder.getCircuitFormLabal(output);

		}

	}

	private String javaObjectToJson(CircuitFormSubmitDetails formSubmitDetails) {
		System.out.println("Inside javaObjectToJson");

		String postBody = "";

		// Creating Object of ObjectMapper define in Jakson Api
		ObjectMapper Obj = new ObjectMapper();

		try {
			postBody = Obj.writeValueAsString(formSubmitDetails);

			// Displaying JSON String
			System.out.println(postBody);
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		return postBody;
	}
	
	
	
}
*/