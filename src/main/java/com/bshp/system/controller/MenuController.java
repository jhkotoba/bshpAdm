package com.bshp.system.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.WebSession;

import com.bshp.common.InitConstruct;
import com.bshp.common.constant.ResponseConstant;
import com.bshp.system.vo.MenuResponseVo;
import com.bshp.system.vo.MenuVo;

import reactor.core.publisher.Mono;

/**
 * 메뉴 컨트롤러
 * @author JeHoon
 *
 */
@Controller
public class MenuController {

	/**
	 * 관리자 메뉴목록 조회
	 * @param response
	 * @param session
	 * @return
	 */
	@ResponseBody
	@PostMapping("/system/getAdminMenuList")
	public Mono<ResponseEntity<MenuResponseVo>> getAdminMenuList(ServerHttpResponse response, WebSession session){
		
		// 응답객체
		MenuResponseVo responseVo = new MenuResponseVo();
		
		try {
			
			// 세션에 저장된 권한 가져오기
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<Integer> authList = (List) session.getAttributes().get("auth");
			// 서버에 저장된 메뉴목록 가져오기
			List<MenuVo> menulist = InitConstruct.menuList;
			
			// 생성될 메뉴목록
			List<MenuVo> adminMenuList = new ArrayList<>();
			
			// 사용자 권한별로 메뉴목록 세팅
			for(Integer auth : authList) {
				for(MenuVo menu : menulist) {
					if(menu.getMenuNo() == auth) {
						adminMenuList.add(menu);
					}
				}
			}
			
			// 사용자 메뉴목록 세팅
			responseVo.setData(adminMenuList);
			responseVo.setResultCode(ResponseConstant.SUCCESS.toString());
			responseVo.setMessage(ResponseConstant.SUCCESS.name());
			
			return Mono.just(ResponseEntity.ok().body(responseVo));
			
		}catch (Exception e) {
			
			// 응답코드 메시지 세팅
			responseVo.setResultCode(ResponseConstant.INTERNAL_SERVER_ERROR.toString());
			responseVo.setMessage(ResponseConstant.INTERNAL_SERVER_ERROR.name());
			
			// 시스템 오류
			return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseVo));
		}
	}
}
