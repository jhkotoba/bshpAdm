package com.bshp.user.vo;

public class PrivateUserVo {

	private String userNo;
	
	private String userId;
	
	private String password;
	
	private String salt;

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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public PublicUserVo createUserVo() {
		PublicUserVo publicUserVo = new PublicUserVo();
		publicUserVo.setUserId(this.userId);
		publicUserVo.setUserNo(this.userNo);
		return publicUserVo;
	}
	
	public PublicUserVo createUserVo(boolean isLogin) {
		PublicUserVo publicUserVo = new PublicUserVo();
		publicUserVo.setUserId(this.userId);
		publicUserVo.setUserNo(this.userNo);
		publicUserVo.setLogin(isLogin);
		return publicUserVo;
	}

}
