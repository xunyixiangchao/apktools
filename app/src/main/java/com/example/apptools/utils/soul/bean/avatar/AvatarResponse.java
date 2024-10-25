package com.example.apptools.utils.soul.bean.avatar;

public class AvatarResponse{
	private int code;
	private AvatarData data;
	private boolean success;
	private String message;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setData(AvatarData data){
		this.data = data;
	}

	public AvatarData getData(){
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
