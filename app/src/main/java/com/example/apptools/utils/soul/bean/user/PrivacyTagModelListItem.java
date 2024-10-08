package com.example.apptools.utils.soul.bean.user;

public class PrivacyTagModelListItem{
	private long createTime;
	private boolean topped;
	private int id;
	private int sort;
	private String tagName;
	private int status;

	public void setCreateTime(long createTime){
		this.createTime = createTime;
	}

	public long getCreateTime(){
		return createTime;
	}

	public void setTopped(boolean topped){
		this.topped = topped;
	}

	public boolean isTopped(){
		return topped;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setSort(int sort){
		this.sort = sort;
	}

	public int getSort(){
		return sort;
	}

	public void setTagName(String tagName){
		this.tagName = tagName;
	}

	public String getTagName(){
		return tagName;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}
