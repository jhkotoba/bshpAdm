package com.bshp.user.vo;

import java.io.Serializable;

/**
 * 사용자 정보 객체(외부)
 * @author JeHoon
 *
 */
public class PublicUserVo implements Serializable{

	private static final long serialVersionUID = -5998929095997271676L;

	private String userNo;
	
	private String userId;

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
}
