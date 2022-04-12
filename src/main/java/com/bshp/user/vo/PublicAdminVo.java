package com.bshp.user.vo;

import java.io.Serializable;

/**
 * 사용자 정보 객체(외부)
 * @author JeHoon
 *
 */
public class PublicAdminVo implements Serializable{

	private static final long serialVersionUID = -5998929095997271676L;

	private String adminNo;
	
	private String adminId;

	public String getAdminNo() {
		return adminNo;
	}

	public void setAdminNo(String adminNo) {
		this.adminNo = adminNo;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	
}
