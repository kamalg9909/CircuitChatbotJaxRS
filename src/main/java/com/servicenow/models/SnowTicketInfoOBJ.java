package com.servicenow.models;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties
public class SnowTicketInfoOBJ {
	
	String number = "";
	String instance_name = "";
	String table_name = "";
	String u_convid = "";
	String item_id = "new";
	String sys_id = "";
	
	
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getSys_id() {
		return sys_id;
	}
	public void setSys_id(String sys_id) {
		this.sys_id = sys_id;
	}
	public String getU_convid() {
		return u_convid;
	}
	public void setU_convid(String u_convid) {
		this.u_convid = u_convid;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getInstance_name() {
		return instance_name;
	}
	public void setInstance_name(String instance_name) {
		this.instance_name = instance_name;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	@Override
	public String toString() {
		return "*******************************************"
				+ "\n" + "SnowTicketInfoOBJ: "
				+ "\n" + "*******************************************"
				+ "\n" + "Snow Instance Name = " + instance_name
				+ "\n" + "Snow Ticket Sys_id = " + sys_id
				+ "\n" + "Snow Ticket Number  = " + number
				+ "\n" +  "Snow Table Name = " + table_name 
				+ "\n" +  "Circuit Conversation ID = " + u_convid
				+ "\n" +  "Circuit Item ID = " + item_id;
	}
	
	
	
	

}
