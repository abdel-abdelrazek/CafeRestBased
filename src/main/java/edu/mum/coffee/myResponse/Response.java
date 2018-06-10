package edu.mum.coffee.myResponse;

import org.springframework.stereotype.Component;

@Component
public class Response {
    private String message;
    private int errorCode;
    
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

   
}
