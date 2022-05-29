package com.bshp.user.vo;

public class PrivateAdminVo {

	private String adminNo;
	
	private String adminId;
	
	private String password;
	
	private String phone;
	
	private String email;
	
	private String useYn;
	
	private String insNo;
	
	private String insDttm;
	
	private String uptNo;
	
	private String uptDttm;	
	
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

	public String getInsNo() {
		return insNo;
	}

	public void setInsNo(String insNo) {
		this.insNo = insNo;
	}

	public String getInsDttm() {
		return insDttm;
	}

	public void setInsDttm(String insDttm) {
		this.insDttm = insDttm;
	}

	public String getUptNo() {
		return uptNo;
	}

	public void setUptNo(String uptNo) {
		this.uptNo = uptNo;
	}

	public String getUptDttm() {
		return uptDttm;
	}

	public void setUptDttm(String uptDttm) {
		this.uptDttm = uptDttm;
	}

	public PublicAdminVo createPublicAdminVo() {
		PublicAdminVo admin = new PublicAdminVo();
		admin.setAdminNo(adminNo);
		admin.setAdminId(adminId);
		admin.setPhone(phone);
		admin.setEmail(email);
		admin.setUseYn(useYn);
		return admin;
	}
}
