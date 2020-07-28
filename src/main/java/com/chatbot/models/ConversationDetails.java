package com.chatbot.models;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties
public class ConversationDetails {

	private String deliveryId;
	private String type;
	private String webhookId;
	private Item item;
	private SubmitFormData submitFormData;

	public SubmitFormData getSubmitFormData() {
		return submitFormData;
	}

	public void setSubmitFormData(SubmitFormData submitFormData) {
		this.submitFormData = submitFormData;
	}

	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWebhookId() {
		return webhookId;
	}

	public void setWebhookId(String webhookId) {
		this.webhookId = webhookId;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "**************************************************"
				+ "\n" + "ConversationDetails: "
				+ "\n" + "**************************************************"
				+ "\n" + "Delivery Id: " + deliveryId 
				+ "\n" + "Type: " + type
				+ "\n" + "Webhook Id: "  + webhookId
				+ "\n" +  item 
				+ "\n" + "submitFormData: " + submitFormData;
	}
	
	

}