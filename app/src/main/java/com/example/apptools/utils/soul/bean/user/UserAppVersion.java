package com.example.apptools.utils.soul.bean.user;

public class UserAppVersion{
	private String userIdEcpt;
	private int appId;
	private int userId;
	private String version;

	public void setUserIdEcpt(String userIdEcpt){
		this.userIdEcpt = userIdEcpt;
	}

	public String getUserIdEcpt(){
		return userIdEcpt;
	}

	public void setAppId(int appId){
		this.appId = appId;
	}

	public int getAppId(){
		return appId;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setVersion(String version){
		this.version = version;
	}

	public String getVersion(){
		return version;
	}
}
