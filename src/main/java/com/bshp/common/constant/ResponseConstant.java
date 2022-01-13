package com.bshp.common.constant;

public enum ResponseConstant {
	
	// 디폴
	SUCCESS("SUCCESS"),
	INTERNAL_SERVER_ERROR("시스템 오류입니다. 관리자에게 문의바랍니다."),	
	
	// 로그인
	ID_NOT_FOUND("가입되지 않은 회원입니다."),
	PASSWORD_DIFFERENT("비밀번호가 일치하지 않습니다.");
	
	private final String message;
	
	ResponseConstant(String message) { 
		this.message = message; 
	}

    public String getMessage() {
    	return message;
    }
	
}
