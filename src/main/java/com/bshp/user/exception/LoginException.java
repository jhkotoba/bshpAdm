package com.bshp.user.exception;

import com.bshp.user.vo.LoginResponseVo;

public class LoginException extends RuntimeException{
	
	private static final long serialVersionUID = 9127113013477100538L;
	
	public enum reason {
		
		// 조회되는 아이디가 없음
		ID_NOT_FOUND,
		
		// 비밀번호가 다른경우
		PASSWORD_DIFFERENT
	}
	
	private reason reason;
	
	private LoginResponseVo loginResponseVo;
	
	public LoginException(reason reason) {
		this.reason = reason;
	}

	public reason getReason() {
		return reason;
	}

	public void setReason(reason reason) {
		this.reason = reason;
	}

	public LoginResponseVo getLoginResponseVo() {
		return loginResponseVo;
	}

	public void setLoginResponseVo(LoginResponseVo loginResponseVo) {
		this.loginResponseVo = loginResponseVo;
	}
}