package com.bshp.user.controller;

import java.security.NoSuchAlgorithmException;

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
		
		PublicUserVo resultPUser = null;
		
		// 회원체크
		try {
			return loginService.loginProcess(login)
				.flatMap(pUser -> {
					
					if(pUser.isLogin()) {
						session.start();
						session.getAttributes().put("user", pUser);
					}else {
						session.invalidate();
						session.getAttributes().remove("user");
					}
					return Mono.defer(() -> Mono.just(ResponseEntity.ok().body(pUser)));
				});
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Mono.defer(() -> Mono.just(ResponseEntity.ok().body(resultPUser)));
		
	}
	
	
}
