package com.example.apptools.utils.soul.bean.user;

public class UserResponse{
	private int code;
	private UserData data;
	private boolean success;
	private String message;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setData(UserData data){
		this.data = data;
	}

	public UserData getData(){
		return data;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}
