package com.bshp.user.vo;

public class PrivateAdminVo {

	private String adminNo;
	
	private String adminId;
	
	private String password;
	
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public PublicAdminVo createPublicAdminVo() {
		PublicAdminVo admin = new PublicAdminVo();
		admin.setAdminNo(adminNo);
		admin.setAdminId(adminId);
		return admin;
	}
}
