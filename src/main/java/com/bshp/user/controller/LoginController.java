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

import com.bshp.common.constant.ResponseConstant;
import com.bshp.user.exception.LoginException;
import com.bshp.user.service.LoginService;
import com.bshp.user.vo.LoginRequestVo;
import com.bshp.user.vo.LoginResponseVo;

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
	 * 가입 페이지
	 * @return
	 */
	@GetMapping("/join")
	public Mono<Rendering> join(WebSession session){
		
		Rendering render = Rendering.view("view/login/join")
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
	public Mono<ResponseEntity<LoginResponseVo>> loginProcess(@RequestBody LoginRequestVo login, WebSession session){		
		
		return loginService.loginProcess(login)
			.flatMap(response -> {
				
				// 세션정보 등록
				if(response.isLogin()) {
					// 세션시작
					session.start();
					// 세션 시간설정 
					session.setMaxIdleTime(Duration.ofHours(3));
					// 세션 유저정보 저장
					session.getAttributes().put("user", response.getData());
				}
				
				// 응답코드 메시지 세팅
				response.setResultCode(ResponseConstant.SUCCESS.toString());
				response.setResultMessage(ResponseConstant.SUCCESS.name());
				
				// 로그인 처리 응답
				return Mono.defer(() -> Mono.just(ResponseEntity.ok().body(response)));
			
			}).onErrorResume(LoginException.class, error -> {
				
				LoginResponseVo response = new LoginResponseVo();
				
				// 응답코드 메시지 세팅
				response.setResultCode(error.getReason().name());
				response.setResultMessage(ResponseConstant.valueOf(error.getReason().name()).getMessage());
				
				// 응답
				return Mono.defer(() -> Mono.just(ResponseEntity.ok().body(response)));
			}).onErrorResume(error -> {
				
				LoginResponseVo response = new LoginResponseVo();
				
				// 응답코드 메시지 세팅
				response.setResultCode(ResponseConstant.INTERNAL_SERVER_ERROR.toString());
				response.setResultMessage(ResponseConstant.INTERNAL_SERVER_ERROR.name());
				
				// 시스템 오류
				return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
			});
	}
}
