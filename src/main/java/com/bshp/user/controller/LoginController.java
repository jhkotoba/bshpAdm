package com.bshp.user.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.WebSession;

import com.bshp.user.exception.LoginException;
import com.bshp.user.service.LoginService;
import com.bshp.user.vo.LoginRequestVo;
import com.bshp.user.vo.PublicUserVo;

import reactor.core.publisher.Mono;

@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;

	/**
	 * 로그인 페이지
	 * @return
	 */
	@GetMapping("/login")
	public Mono<Rendering> login(WebSession session){
		
		Rendering render = Rendering.view("view/login/login")
			.status(HttpStatus.OK)
			.build();
		
		return Mono.just(render);
	}
	
	/**
	 * 로그인 처리
	 * @return
	 */
	@ResponseBody
	@PostMapping("/login/loginProcess")
	public Mono<ResponseEntity<PublicUserVo>> loginProcess(@RequestBody LoginRequestVo login, WebSession session){		
		
		// 회원체크
		try {
			return loginService.loginProcess(login)
				.flatMap(pUser -> {
					
					// 세션정보 등록
					if(pUser.isLogin()) {
						// 세션시작
						session.start();
						// 세션 시간설정 
						session.setMaxIdleTime(Duration.ofHours(3));
						// 세션 유저정보 저장
						session.getAttributes().put("user", pUser);
					}else {
						throw new LoginException(LoginException.PASSWORD_DIFFERENT); 
					}
				
					return Mono.defer(() -> Mono.just(ResponseEntity.ok().body(pUser)));
				});
		
		// 로그인 예외
		} catch (LoginException e) {
			return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new PublicUserVo()));
		
		// 시스템 오류
		} catch (Exception e) {
			return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
		}
	}
}
