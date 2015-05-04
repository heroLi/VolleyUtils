package com.li.volley.response;

import java.io.Serializable;


/**
 * 一嗨状态类
 * 
 * @author 18834
 * **/
public class EhaiResult implements Serializable {

	private boolean IsSuccess;

	private String Message;

	public boolean isIsSuccess() {
		return IsSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		IsSuccess = isSuccess;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}
	
}
