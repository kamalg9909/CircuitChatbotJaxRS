package com.servicenow.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Result {

	private String number;

	private String short_description;

	private Assignment_group assignment_group;

	private String description;

	private Company company;

	private String state;

	private String category;

	private String priority;

	private String sys_id;

	private Assigned_To assigned_to;
	
	public Result() {}
	
	public Assigned_To getAssigned_to() {
		return assigned_to;
	}

	public void setAssigned_to(Assigned_To assigned_to) {
		this.assigned_to = assigned_to;
	}

	public String getSys_id() {
		return sys_id;
	}

	public void setSys_id(String sys_id) {
		this.sys_id = sys_id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getShort_description() {
		return short_description;
	}

	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}

	public Assignment_group getAssignment_group() {
		return assignment_group;
	}

	public void setAssignment_group(Assignment_group assignment_group) {
		this.assignment_group = assignment_group;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "Number  : " + number + "\n" + "Category  : " + category + "\n" + "Company  : " + company + "\n"
				+ "Priority  : " + priority + "\n" + "Short Description  : " + short_description + "\n"
				+ "Assignment Group  : " + assignment_group + "\n" + "Description  : " + description + "\n" + "State  : "
				+ state + "\n" + "Assigned To  : " + assigned_to;

	}

}
