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
	
	private String phone;
	
	private String email;
	
	private String useYn;

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
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
}
