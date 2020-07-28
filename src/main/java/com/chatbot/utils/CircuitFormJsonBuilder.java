package com.chatbot.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.chatbot.models.RequestFormEnums;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicenow.models.Result;
import com.servicenow.models.SnowTicketInfoOBJ;

public class CircuitFormJsonBuilder {

	Map formMetaData = new LinkedHashMap();
	List controls = controls = new ArrayList();

	public String getCircuitAssignedToForm(SnowTicketInfoOBJ ticketInfoObj, Result result) {
		System.out.println("Inside getSendTicketNotificationForm()");

		// Creating FormID : 'FormType_Name_ConversationId
		String formId = javaObjectToJsonString(ticketInfoObj);
		formMetaData.put("id", "AssignedToForm&" + formId);
		formMetaData.put("title", "Assign Ticket Form");
		getLabelControl(result.toString());
		getInputControl("Assigned To", "assigned_to", "Email ID (Optional)", "small");
		getSubmitButton("Assign Ticket");

		formMetaData.put("controls", controls);
		JSONObject obj = new JSONObject(formMetaData);

		return obj.toString();

	}

	public String sendSuccessMessageToCircuit(Result result) {
		System.out.println("Inside getSendTicketNotificationForm()");

		formMetaData.put("id", "Success Response");
		formMetaData.put("title", "Updated Ticket Information");
		getLabelControl(result.toString());

		formMetaData.put("controls", controls);
		JSONObject obj = new JSONObject(formMetaData);

		return obj.toString();

	}

	public String notifyErrorToUserFormMetadata(String statusString) {
		System.out.println("Inside notifyErrorToUser()");
		formMetaData.put("id", "Response Error");
		formMetaData.put("title", "");
		getLabelControl(statusString);

		formMetaData.put("controls", controls);
		JSONObject obj = new JSONObject(formMetaData);

		return obj.toString();

	}

	public String getCircuitFormLabal(String label) {

		formMetaData.put("id", "Response");
		formMetaData.put("title", "");
		getLabelControl(label);
		formMetaData.put("controls", controls);
		JSONObject obj = new JSONObject(formMetaData);
		return obj.toString();

	}

	public String getActionsForm(SnowTicketInfoOBJ ticketInfoOBJ, String userAction) {
		System.out.println("getActionsForm()");

		if (userAction.contains("create")) {
			System.out.println("Inside create If");
			String formType = RequestFormEnums.CreateTicketForm.toString();
			createTicketForm(ticketInfoOBJ, formType);
		} else if (userAction.contains("update")) {
			System.out.println("Inside update If");
			String formType = RequestFormEnums.UpdateTicketForm.toString();
			updateTicketForm(ticketInfoOBJ, formType);
		} else if (userAction.contains("hold")) {
			System.out.println("Inside putonhold If");
			String formType = RequestFormEnums.UpdateTicketForm.toString();
			onHoldTicketForm(ticketInfoOBJ, formType);
		} else if (userAction.contains("close")) {
			System.out.println("Inside closeticket If");
			String formType = RequestFormEnums.UpdateTicketForm.toString();
			closeOrResolvedTicketForm(ticketInfoOBJ, formType);
		}
		if (userAction.contains("resolve")) {
			System.out.println("Inside resolveticket If");
			String formType = RequestFormEnums.UpdateTicketForm.toString();
			closeOrResolvedTicketForm(ticketInfoOBJ, formType);
		}

		JSONObject obj = new JSONObject(formMetaData);
		return obj.toString();
	}

	private void createTicketForm(SnowTicketInfoOBJ ticketInfoOBJ, String formType) {

		// Fields

		// Short Description : short_description
		// Category : category : inquiry,software,hardware,network,database
		// Description : description
		// Impact : impact : High-1,Medium-2,Low-3
		// Assignment Group : assignment_group : Service Desk,Software,Hardware,Network
		System.out.println("Inside createTicketForm()");

		getInputControl("ServiceNow Instance Name", "instance_name", "Enter ServiceNow Instance Name", "medium");
		getInputControl("Short Description", "short_description", "Enter Short Description", "medium");

		Map categoryDropdown = new LinkedHashMap();
		categoryDropdown.put("title", "Category");
		categoryDropdown.put("type", "DROPDOWN");
		categoryDropdown.put("name", "category");

		List<Map<String, String>> categoryDropdownOptions = new ArrayList<Map<String, String>>();
		Map<String, String> categoryOption1 = new LinkedHashMap<String, String>();
		categoryOption1.put("text", "Select the Category");
		categoryOption1.put("value", "");
		Map<String, String> categoryOption2 = new LinkedHashMap<String, String>();
		categoryOption2.put("text", "Inquiry");
		categoryOption2.put("value", "inquiry");
		Map<String, String> categoryOption3 = new LinkedHashMap<String, String>();
		categoryOption3.put("text", "Software");
		categoryOption3.put("value", "software");
		Map<String, String> categoryOption4 = new LinkedHashMap<String, String>();
		categoryOption4.put("text", "Hardware");
		categoryOption4.put("value", "hardware");
		Map<String, String> categoryOption5 = new LinkedHashMap<String, String>();
		categoryOption5.put("text", "Network");
		categoryOption5.put("value", "network");
		Map<String, String> categoryOption6 = new LinkedHashMap<String, String>();
		categoryOption6.put("text", "Database");
		categoryOption6.put("value", "database");

		categoryDropdown.put("defaultValue", "");

		categoryDropdownOptions.add(categoryOption1);
		categoryDropdownOptions.add(categoryOption2);
		categoryDropdownOptions.add(categoryOption3);
		categoryDropdownOptions.add(categoryOption4);
		categoryDropdownOptions.add(categoryOption5);
		categoryDropdownOptions.add(categoryOption6);

		categoryDropdown.put("options", categoryDropdownOptions);
		controls.add(categoryDropdown);

		getInputControl("Description", "description", "Enter Description", "large");
		getImpactDropDown();

		// Assignment Group Dropdown
		Map groupDropdown = new LinkedHashMap();
		groupDropdown.put("title", "Assignment Group");
		groupDropdown.put("type", "DROPDOWN");
		groupDropdown.put("name", "assignment_group");

		List<Map<String, String>> groupDropdownOptions = new ArrayList<Map<String, String>>();
		Map<String, String> groupOption1 = new LinkedHashMap<String, String>();
		groupOption1.put("text", "Select the Assignment Group");
		groupOption1.put("value", "");
		Map<String, String> groupOption2 = new LinkedHashMap<String, String>();
		groupOption2.put("text", "Service Desk");
		groupOption2.put("value", "Service Desk");
		Map<String, String> groupOption3 = new LinkedHashMap<String, String>();
		groupOption3.put("text", "Software");
		groupOption3.put("value", "Software");
		Map<String, String> groupOption4 = new LinkedHashMap<String, String>();
		groupOption4.put("text", "Hardware");
		groupOption4.put("value", "Hardware");
		Map<String, String> groupOption5 = new LinkedHashMap<String, String>();
		groupOption5.put("text", "Network");
		groupOption5.put("value", "Network");

		categoryDropdown.put("defaultValue", "");

		groupDropdownOptions.add(groupOption1);
		groupDropdownOptions.add(groupOption2);
		groupDropdownOptions.add(groupOption3);
		groupDropdownOptions.add(groupOption4);
		groupDropdownOptions.add(groupOption5);

		groupDropdown.put("options", groupDropdownOptions);
		controls.add(groupDropdown);

		getSubmitButton("Create Ticket");

		String formId = javaObjectToJsonString(ticketInfoOBJ);
		formMetaData.put("id", formType + "&" + formId);
		formMetaData.put("title", "Create Ticket Form");
		formMetaData.put("controls", controls);

	}

	private void updateTicketForm(SnowTicketInfoOBJ ticketInfoOBJ, String formType) {
		// Fields
		// Ticket Number
		// State : state : In Progress, Resolved, Closed, OnHold
		// Impact : impact
		// Assigned to : assigned_to
		// Work notes : work_notes
		System.out.println("Inside updateTicketForm()");

		String instanceName = ticketInfoOBJ.getInstance_name();
		if (instanceName == null || instanceName.equals("")) {
			getInputControl("ServiceNow Instance Name", "instance_name", "Enter ServiceNow Instance Name", "medium");
			getInputControl("Incident Number", "number", "Enter Incident Number", "medium");
		}

		getImpactDropDown();
		getInputControl("Work Notes", "work_notes", "Enter work notes", "medium");
		getSubmitButton("Update Ticket");

		String formId = javaObjectToJsonString(ticketInfoOBJ);
		formMetaData.put("id", formType + "&" + formId);
		formMetaData.put("title", "Update Ticket Form");
		formMetaData.put("controls", controls);

	}

	private void closeOrResolvedTicketForm(SnowTicketInfoOBJ ticketInfoOBJ, String formType) {
		// Fields
		// Ticket Number
		// Resolution code : close_code : Solved(Permanently),Closed(Resolved by
		// Caller),Not Solved(Not Reproducible)
		// Resolution notes : close_notes
		System.out.println("Inside closeTicketForm()");

		String instanceName = ticketInfoOBJ.getInstance_name();
		if (instanceName == null || instanceName.equals("")) {
			getInputControl("ServiceNow Instance Name", "instance_name", "Enter ServiceNow Instance Name", "medium");
			getInputControl("Incident Number", "number", "Enter Incident Number", "medium");
		}
		
		// State Dropdown
		Map stateDropdown = new LinkedHashMap();
		stateDropdown.put("title", "State");
		stateDropdown.put("type", "DROPDOWN");
		stateDropdown.put("name", "state");

		List<Map<String, String>> stateDropdownOptions = new ArrayList<Map<String, String>>();
		Map<String, String> stateOption1 = new LinkedHashMap<String, String>();
		stateOption1.put("text", "Closed");
		stateOption1.put("value", "Closed");

		stateDropdown.put("defaultValue", "Closed");

		stateDropdownOptions.add(stateOption1);
		stateDropdown.put("options", stateDropdownOptions);
		controls.add(stateDropdown);

		// Resolution code Dropdown
		Map resolutionCodeDropdown = new LinkedHashMap();
		resolutionCodeDropdown.put("title", "Resolution code");
		resolutionCodeDropdown.put("type", "DROPDOWN");
		resolutionCodeDropdown.put("name", "close_code");

		List<Map<String, String>> resolutionCodeDropdownOptions = new ArrayList<Map<String, String>>();
		Map<String, String> resolutionCodeOption1 = new LinkedHashMap<String, String>();
		resolutionCodeOption1.put("text", "Select the Resolution Code");
		resolutionCodeOption1.put("value", "");
		Map<String, String> resolutionCodeOption2 = new LinkedHashMap<String, String>();
		resolutionCodeOption2.put("text", "Solved (Permanently)");
		resolutionCodeOption2.put("value", "Solved (Permanently)");
		Map<String, String> resolutionCodeOption3 = new LinkedHashMap<String, String>();
		resolutionCodeOption3.put("text", "Closed/Resolved by Caller");
		resolutionCodeOption3.put("value", "Closed/Resolved by Caller");
		Map<String, String> resolutionCodeOption4 = new LinkedHashMap<String, String>();
		resolutionCodeOption4.put("text", "Not Solved (Not Reproducible)");
		resolutionCodeOption4.put("value", "Not Solved (Not Reproducible)");

		resolutionCodeDropdown.put("defaultValue", "");

		resolutionCodeDropdownOptions.add(resolutionCodeOption1);
		resolutionCodeDropdownOptions.add(resolutionCodeOption2);
		resolutionCodeDropdownOptions.add(resolutionCodeOption3);
		resolutionCodeDropdownOptions.add(resolutionCodeOption4);

		resolutionCodeDropdown.put("options", resolutionCodeDropdownOptions);
		controls.add(resolutionCodeDropdown);

		// Resolution notes Input

		getInputControl("Resolution Notes", "close_notes", "Enter Resolution notes", "large");
		getSubmitButton("Close");

		String formId = javaObjectToJsonString(ticketInfoOBJ);
		formMetaData.put("id", formType + "&" + formId);
		formMetaData.put("title", "Close/Resolve Ticket Form");
		formMetaData.put("controls", controls);

	}

	private void onHoldTicketForm(SnowTicketInfoOBJ ticketInfoOBJ, String formType) {
		// Fields
		// Ticket Number
		// On hold reason : hold_reason : Awaiting Caller(1),Awaiting Change(5),Awaiting
		// Problem(3),Awaiting Vendor(4)
		System.out.println("Inside onHoldTicketForm()");
		
		String instanceName = ticketInfoOBJ.getInstance_name();
		if (instanceName == null || instanceName.equals("")) {
			getInputControl("ServiceNow Instance Name", "instance_name", "Enter ServiceNow Instance Name", "medium");
			getInputControl("Incident Number", "number", "Enter Incident Number", "medium");
		}

		// State Dropdown
		Map stateDropdown = new LinkedHashMap();
		stateDropdown.put("title", "State");
		stateDropdown.put("type", "DROPDOWN");
		stateDropdown.put("name", "state");

		List<Map<String, String>> stateDropdownOptions = new ArrayList<Map<String, String>>();
		Map<String, String> stateOption1 = new LinkedHashMap<String, String>();
		stateOption1.put("text", "On Hold");
		stateOption1.put("value", "On Hold");

		stateDropdown.put("defaultValue", "On Hold");

		stateDropdownOptions.add(stateOption1);
		stateDropdown.put("options", stateDropdownOptions);
		controls.add(stateDropdown);

		// On hold reason Dropdown
		Map onholdReasonDropdown = new LinkedHashMap();
		onholdReasonDropdown.put("title", "On Hold Reason");
		onholdReasonDropdown.put("type", "DROPDOWN");
		onholdReasonDropdown.put("name", "hold_reason");

		List<Map<String, String>> onholdReasonDropdownOptions = new ArrayList<Map<String, String>>();
		Map<String, String> onholdReasonOption1 = new LinkedHashMap<String, String>();
		onholdReasonOption1.put("text", "Select the Reason");
		onholdReasonOption1.put("value", "");
		Map<String, String> onholdReasonOption2 = new LinkedHashMap<String, String>();
		onholdReasonOption2.put("text", "Awaiting Caller");
		onholdReasonOption2.put("value", "1");
		Map<String, String> onholdReasonOption3 = new LinkedHashMap<String, String>();
		onholdReasonOption3.put("text", "Awaiting Change");
		onholdReasonOption3.put("value", "5");
		Map<String, String> onholdReasonOption4 = new LinkedHashMap<String, String>();
		onholdReasonOption4.put("text", "Awaiting Problem");
		onholdReasonOption4.put("value", "3");
		Map<String, String> onholdReasonOption5 = new LinkedHashMap<String, String>();
		onholdReasonOption5.put("text", "Awaiting Vendor");
		onholdReasonOption5.put("value", "4");

		onholdReasonDropdown.put("defaultValue", "");

		onholdReasonDropdownOptions.add(onholdReasonOption1);
		onholdReasonDropdownOptions.add(onholdReasonOption2);
		onholdReasonDropdownOptions.add(onholdReasonOption3);
		onholdReasonDropdownOptions.add(onholdReasonOption4);
		onholdReasonDropdownOptions.add(onholdReasonOption5);

		onholdReasonDropdown.put("options", onholdReasonDropdownOptions);
		controls.add(onholdReasonDropdown);
		getInputControl("Work Notes", "work_notes", "Enter work notes", "medium");
		getSubmitButton("Hold");

		String formId = javaObjectToJsonString(ticketInfoOBJ);
		formMetaData.put("id", formType + "&" + formId);
		formMetaData.put("title", "OnHold Form");
		formMetaData.put("controls", controls);
	}

	private void getLabelControl(String label) {

		Map labelControl = new LinkedHashMap();
		labelControl.put("type", "LABEL");
		labelControl.put("text", "<b>" + label + "</b>");
		controls.add(labelControl);

	}

	private void getInputControl(String inputTitle, String inputName, String text, String size) {

		Map inputControl = new LinkedHashMap();
		inputControl.put("title", inputTitle);
		inputControl.put("type", "INPUT");
		inputControl.put("name", inputName);
		inputControl.put("text", "Enter " + text);
		inputControl.put("size", size);
		controls.add(inputControl);

	}

	private void getImpactDropDown() {

		Map impactDropdown = new LinkedHashMap();
		impactDropdown.put("title", "Impact");
		impactDropdown.put("type", "DROPDOWN");
		impactDropdown.put("name", "impact");

		List<Map<String, String>> impactDropdownOptions = new ArrayList<Map<String, String>>();
		Map<String, String> impactOption1 = new LinkedHashMap<String, String>();
		impactOption1.put("text", "Select the Impact");
		impactOption1.put("value", "");
		Map<String, String> impactOption2 = new LinkedHashMap<String, String>();
		impactOption2.put("text", "Low");
		impactOption2.put("value", "3");
		Map<String, String> impactOption3 = new LinkedHashMap<String, String>();
		impactOption3.put("text", "Medium");
		impactOption3.put("value", "2");
		Map<String, String> impactOption4 = new LinkedHashMap<String, String>();
		impactOption4.put("text", "High");
		impactOption4.put("value", "1");

		impactDropdown.put("defaultValue", "");

		impactDropdownOptions.add(impactOption1);
		impactDropdownOptions.add(impactOption2);
		impactDropdownOptions.add(impactOption3);
		impactDropdownOptions.add(impactOption4);

		impactDropdown.put("options", impactDropdownOptions);
		controls.add(impactDropdown);

	}

	private void getSubmitButton(String buttonName) {
		// Submit Button
		Map buttoncontrol = new LinkedHashMap();
		buttoncontrol.put("type", "BUTTON");
		buttoncontrol.put("text", buttonName);
		controls.add(buttoncontrol);

	}

	// Converting SnowWebhookResultOBJ into a json string.
	private String javaObjectToJsonString(SnowTicketInfoOBJ ticketInfoOBJ) {

		ObjectMapper mapper = new ObjectMapper();
		String formId = "";

		try {
			formId = mapper.writeValueAsString(ticketInfoOBJ);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(formId);
		return formId;
	}
}
