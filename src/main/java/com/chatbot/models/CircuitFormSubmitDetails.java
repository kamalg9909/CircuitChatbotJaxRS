package com.chatbot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties
@JsonInclude(Include.NON_NULL)
public class CircuitFormSubmitDetails {
	
	private String number;
	private String short_description;
	private String category;
	private String description;
	private String assignment_group;
	private String state;
	private String assigned_to;
	private String work_notes;
	private String close_code;
	private String close_notes;
	private String hold_reason;
	private String impact;
	
	public String getImpact() {
		return impact;
	}
	public void setImpact(String impact) {
		this.impact = impact;
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
	public String getAssignment_group() {
		return assignment_group;
	}
	public void setAssignment_group(String assignment_group) {
		this.assignment_group = assignment_group;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAssigned_to() {
		return assigned_to;
	}
	public void setAssigned_to(String assigned_to) {
		this.assigned_to = assigned_to;
	}
	public String getWork_notes() {
		return work_notes;
	}
	public void setWork_notes(String work_notes) {
		this.work_notes = work_notes;
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
	public String getHold_reason() {
		return hold_reason;
	}
	public void setHold_reason(String hold_reason) {
		this.hold_reason = hold_reason;
	}

}
