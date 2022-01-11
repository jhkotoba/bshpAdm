package com.bshp.user.vo;

import java.io.Serializable;

public class PublicUserVo implements Serializable{

	private static final long serialVersionUID = -5998929095997271676L;

	private String userNo;
	
	private String userId;
	
	private boolean isLogin = false;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	
}
