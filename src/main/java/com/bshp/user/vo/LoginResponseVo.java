package com.bshp.user.vo;

import com.bshp.common.vo.ResponseVo;

/**
 * 로그인 응답 객체
 * @author JeHoon
 *
 */
public class LoginResponseVo extends ResponseVo<PublicAdminVo> {
	
	// 로그인 여부
	private boolean isLogin = false;

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public void setPublicAdminVo(PrivateAdminVo user, boolean isLogin) {		
		this.data = user.createPublicAdminVo();
		this.isLogin = isLogin;
	}		
}
