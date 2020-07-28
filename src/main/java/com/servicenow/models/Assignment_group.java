package com.servicenow.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public  class Assignment_group
{
    private String display_value;

    private String link;

	public Assignment_group() {
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