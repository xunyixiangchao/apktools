package com.example.apptools.utils.soul.bean.sign;

public class SignResponse{
	private int code;
	private Data data;
	private boolean success;
	private String message;

	public int getCode(){
		return code;
	}

	public Data getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}
