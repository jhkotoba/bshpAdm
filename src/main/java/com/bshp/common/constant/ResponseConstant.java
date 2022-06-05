package com.bshp.common.constant;

public enum ResponseConstant {
	
	// 공통(코드)
	SUCCESS("SUCCESS"),
	FAIL("FAIL"),
	
	// 공통
	INTERNAL_SERVER_ERROR("시스템 오류입니다. 관리자에게 문의바랍니다."),
	INSERT_FAIL("저장에 실패하였습니다."),
	UPDATE_FAIL("수정에 실패하였습니다."),
	
	// 로그인
	ID_NOT_FOUND("가입되지 않은 회원입니다."),
	PASSWORD_DIFFERENT("비밀번호가 일치하지 않습니다."),
	
	// 회원신청
	JOIN_SUCCESS("회원신청에 성공하였습니다."),
	ID_ALREADY_EXIST("동일한 ID가 존재합니다."),
	INCORRECT_PHONE("유효하지 않음 전화번호 입니다."),
	INCORRECT_EMAIL("유효하지 않은 이메일 입니다.");
	
	private final String message;
	
	ResponseConstant(String message) { 
		this.message = message; 
	}

    public String getMessage() {
    	return message;
    }
	
}
