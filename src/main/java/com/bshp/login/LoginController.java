package com.bshp.login;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.WebSession;

import reactor.core.publisher.Mono;

@Controller
public class LoginController {

	/**
	 * 메인 페이지
	 * @return
	 */
	@GetMapping("/login")
	public Mono<Rendering> main(WebSession session){
		
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
	@GetMapping("/login/loginProcess")
	public Mono<String> loginProcess(){
		
		return Mono.empty();
		
	}
	
	
}
