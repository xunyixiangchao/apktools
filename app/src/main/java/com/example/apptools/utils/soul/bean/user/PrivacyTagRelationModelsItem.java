package com.example.apptools.utils.soul.bean.user;

public class PrivacyTagRelationModelsItem{
	private boolean commonlyOwn;
	private int authTagType;
	private int id;
	private String tagName;
	private int categoryId;
	private int authTaskCompleteType;
	private int status;

	public void setCommonlyOwn(boolean commonlyOwn){
		this.commonlyOwn = commonlyOwn;
	}

	public boolean isCommonlyOwn(){
		return commonlyOwn;
	}

	public void setAuthTagType(int authTagType){
		this.authTagType = authTagType;
	}

	public int getAuthTagType(){
		return authTagType;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTagName(String tagName){
		this.tagName = tagName;
	}

	public String getTagName(){
		return tagName;
	}

	public void setCategoryId(int categoryId){
		this.categoryId = categoryId;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public void setAuthTaskCompleteType(int authTaskCompleteType){
		this.authTaskCompleteType = authTaskCompleteType;
	}

	public int getAuthTaskCompleteType(){
		return authTaskCompleteType;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}
