package com.bshp.system.vo;

/**
 * 메뉴 Vo
 * @author JeHoon
 *
 */
public class MenuVo {
	
	// 메뉴번호
	private int menuNo;
	
	// 메뉴명
	private String menuNm;
	
	// 메뉴URL
	private String menuUrl;
	
	// 메뉴단계
	private int MenuLv;
	
	// 메뉴순번
	private int menuSeq;
	
	// 그룹번호
	private int groupNo;
	
	// 전시여부
	private String dispYn;
	
	// 사용여부
	private String useYn;
	
	// 등록자
	private int insNo;
	
	// 등록일시
	private String insDttm;
	
	// 수정자
	private int uptNo;
	
	// 수정일시
	private String uptDttm;
	
	public int getMenuNo() {
		return menuNo;
	}
	public void setMenuNo(int menuNo) {
		this.menuNo = menuNo;
	}
	public String getMenuNm() {
		return menuNm;
	}
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public int getMenuLv() {
		return MenuLv;
	}
	public void setMenuLv(int menuLv) {
		MenuLv = menuLv;
	}
	public int getMenuSeq() {
		return menuSeq;
	}
	public void setMenuSeq(int menuSeq) {
		this.menuSeq = menuSeq;
	}
	public int getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}
	public String getDispYn() {
		return dispYn;
	}
	public void setDispYn(String dispYn) {
		this.dispYn = dispYn;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
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
	public int getUptNo() {
		return uptNo;
	}
	public void setUptNo(int uptNo) {
		this.uptNo = uptNo;
	}
	public String getUptDttm() {
		return uptDttm;
	}
	public void setUptDttm(String uptDttm) {
		this.uptDttm = uptDttm;
	}
}
