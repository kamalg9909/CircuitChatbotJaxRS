package com.servicenow.models;

public class Assigned_To {
	
	private String display_value;

    private String link;

	public Assigned_To() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Assigned_To(String display_value, String link) {
		super();
		this.display_value = display_value;
		this.link = link;
	}

	public String getDisplay_value() {
		return display_value;
	}

	public void setDisplay_value(String display_value) {
		this.display_value = display_value;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return display_value;
	}
}
