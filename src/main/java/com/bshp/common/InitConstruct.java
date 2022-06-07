package com.bshp.common;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bshp.system.service.MenuService;
import com.bshp.system.vo.MenuVo;

@Component
public class InitConstruct {
	
	/**
	 * 메뉴 서비스
	 */
	@Autowired
	private MenuService menuService;
	
	// 메뉴목록
	public static List<MenuVo> menuList = null;
	
	/**
	 * 메뉴정보 초기 조회
	 */
	@PostConstruct
	public void loginPostConstruct() {
		
		// 메뉴목록 조회
		menuList = menuService.selectMenuList().collectList().block();
	}

}
