package com.bshp.user.vo;

import java.lang.reflect.Field;
import java.util.Map;


public class UserVo {

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
	
	public UserVo(Map<String, Object> user) {
		
		try {
			for (Field field : user.getClass().getDeclaredFields()) {			
				field.set(user, user.get(field.getName()));
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {		
			e.printStackTrace();
		}
	}
}
