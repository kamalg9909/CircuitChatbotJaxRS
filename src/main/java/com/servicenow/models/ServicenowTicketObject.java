package com.servicenow.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


//This class with be used as generic object to insert or update the snow ticket

@JsonInclude(Include.NON_NULL)
public class ServicenowTicketObject {

	private String category;
	
	private String description;

	private String short_description;

	private String assignment_group;

	private String state;

	private String priority;

	private String sys_id;

	private String assigned_to;
	
	private String close_code;
	
	private String close_notes;
	
	private String work_notes;
	
	private String hold_reason;
	

	public ServicenowTicketObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShort_description() {
		return short_description;
	}

	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}

	public String getAssignment_group() {
		return assignment_group;
	}

	public void setAssignment_group(String assignment_group) {
		this.assignment_group = assignment_group;
	}

	public String getHold_reason() {
		return hold_reason;
	}

	public void setHold_reason(String hold_reason) {
		this.hold_reason = hold_reason;
	}

	public String getClose_code() {
		return close_code;
	}

	public void setClose_code(String close_code) {
		this.close_code = close_code;
	}

	public String getClose_notes() {
		return close_notes;
	}

	public void setClose_notes(String close_notes) {
		this.close_notes = close_notes;
	}

	public String getWork_notes() {
		return work_notes;
	}

	public void setWork_notes(String work_notes) {
		this.work_notes = work_notes;
	}

	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getSys_id() {
		return sys_id;
	}

	public void setSys_id(String sys_id) {
		this.sys_id = sys_id;
	}

	public String getAssigned_to() {
		return assigned_to;
	}

	public void setAssigned_to(String assigned_to) {
		this.assigned_to = assigned_to;
	}

	@Override
	public String toString() {
		return "ServicenowTicketObject [state=" + state + ", priority=" + priority + ", sys_id=" + sys_id
				+ ", assigned_to=" + assigned_to + ", close_code=" + close_code + ", close_notes=" + close_notes
				+ ", work_notes=" + work_notes + ", hold_reason=" + hold_reason + "]";
	}

	
}
