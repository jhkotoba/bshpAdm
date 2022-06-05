package com.bshp.user.exception;

public class JoinException extends RuntimeException{
	
	private static final long serialVersionUID = 9127113013477100538L;
	
	public enum reason {
		
		// 등록하려는 아이디가 존재함
		ID_ALREADY_EXIST,
		
		// 잘못된 비밀번호
		INCORRECT_PASSWORD,
		
		// 잘못된 전화번호 
		INCORRECT_PHONE,
				
		// 잘못된 이메일
		INCORRECT_EMAIL,
		
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