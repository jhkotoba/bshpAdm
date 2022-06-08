package com.bshp.system.vo;

/**
 * 권한 Vo
 * @author JeHoon
 *
 */
public class AuthVo {
	
	// 사용자번호
	private int adminNo;
	
	// 메뉴번호
	private int menuNo;
	
	// 등록자
	private int insNo;
	
	// 등록일시
	private String insDttm;

	public int getAdminNo() {
		return adminNo;
	}

	public void setAdminNo(int adminNo) {
		this.adminNo = adminNo;
	}

	public int getMenuNo() {
		return menuNo;
	}

	public void setMenuNo(int menuNo) {
		this.menuNo = menuNo;
	}

	public int getInsNo() {
		return insNo;
	}

	public void setInsNo(int insNo) {
		this.insNo = insNo;
	}

	public String getInsDttm() {
		return insDttm;
	}

	public void setInsDttm(String insDttm) {
		this.insDttm = insDttm;
	}
}
