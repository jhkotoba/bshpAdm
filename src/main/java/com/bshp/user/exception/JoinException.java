package com.bshp.user.exception;

public class JoinException extends RuntimeException{
	
	private static final long serialVersionUID = 9127113013477100538L;
	
	public enum reason {
		
		// 등록하려는 아이디가 존재함
		ID_ALREADY_EXIST, 
		
		// 등록실패(INSERT)
		INSERT_FAIL
	}
	
	private reason reason;
	
	public JoinException(reason reason) {
		this.reason = reason;
	}

	public reason getReason() {
		return reason;
	}

	public void setReason(reason reason) {
		this.reason = reason;
	}
}