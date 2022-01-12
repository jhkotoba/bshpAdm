package com.bshp.user.vo;

public class PrivateUserVo {

	private String userNo;
	
	private String userId;
	
	private String password;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public PublicUserVo createPublicUserVo() {
		PublicUserVo user = new PublicUserVo();
		user.setUserNo(userNo);
		user.setUserId(userId);
		return user;
	}
}
