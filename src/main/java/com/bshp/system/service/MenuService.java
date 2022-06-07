package com.bshp.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bshp.system.repository.MenuRepository;
import com.bshp.system.vo.MenuVo;

import reactor.core.publisher.Flux;

/**
 * 메뉴 서비스
 * @author JeHoon
 *
 */
@Service
public class MenuService {
	
	/**
	 * 메뉴 저장소
	 */
	@Autowired
	private MenuRepository menuRepository;
	
	/**
	 * 메뉴목록 조회
	 * @return
	 */
	public Flux<MenuVo> selectMenuList(){
		return menuRepository.selectMenuList();
	}
}
