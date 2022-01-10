package com.bshp.user.exception;


public class LoginException extends RuntimeException{
	
	private static final long serialVersionUID = 9127113013477100538L;
	
	public static final String PASSWORD_DIFFERENT = "1000";
	
	private String code;
	
	public LoginException(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}