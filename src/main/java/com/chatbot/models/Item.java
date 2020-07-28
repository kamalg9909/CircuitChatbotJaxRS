package com.chatbot.models;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties
public class Item {
	
	private String type;
	private String parentId;
	private String itemId;
	private String convId;
	private Text text;
	private Long creationTime;
	private Long modificationTime;
	private String creatorId;
	private Boolean includeInUnreadCount;
	
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getConvId() {
		return convId;
	}
	public void setConvId(String convId) {
		this.convId = convId;
	}
	public Text getText() {
		return text;
	}
	public void setText(Text text) {
		this.text = text;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public Boolean getIncludeInUnreadCount() {
		return includeInUnreadCount;
	}
	public void setIncludeInUnreadCount(Boolean includeInUnreadCount) {
		this.includeInUnreadCount = includeInUnreadCount;
	}
	
	public Long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Long creationTime) {
		this.creationTime = creationTime;
	}

	public Long getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Long modificationTime) {
		this.modificationTime = modificationTime;
	}

	@Override
	public String toString() {
		return "**************************************************"
				+ "\n" + "Item: "
				+ "\n" + "**************************************************" 
				+ "\n" + "Type: " + type
				+ "\n" + "Parent Id: " + parentId
				+ "\n" + "Item Id: " + itemId 
				+ "\n" + "Conv Id: " + convId 
				+ "\n" +  text
				+ "\n" + "CreationTime: " + creationTime 
				+ "\n" + "ModificationTime: " + modificationTime 
				+ "\n" + "CreatorId: "+ creatorId 
				+ "\n" + "IncludeInUnreadCount:" + includeInUnreadCount;
	}
	

}
