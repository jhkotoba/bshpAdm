package com.bshp.user.controller;

import java.time.Duration;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.WebSession;

import com.bshp.common.constant.ResponseConstant;
import com.bshp.common.property.AES256Properties;
import com.bshp.common.util.AES256Util;
import com.bshp.common.util.CommonUtil;
import com.bshp.system.service.AuthService;
import com.bshp.user.exception.LoginException;
import com.bshp.user.service.LoginService;
import com.bshp.user.vo.LoginRequestVo;
import com.bshp.user.vo.LoginResponseVo;

import reactor.core.publisher.Mono;

/**
 * 로그인 컨트롤러
 * @author JeHoon
 *
 */
@Controller
public class LoginController {
	
	/**
	 * 로그인 서비스
	 */
	@Autowired
	private LoginService loginService;
	
	/**
	 * 권한 서비스
	 */
	@Autowired
	private AuthService authService;
	
	/**
	 * 암복호화 객체
	 */
	@Autowired
	private AES256Properties aes256;
	
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
	 * @param login
	 * @param response
	 * @param session
	 * @return
	 */
	@ResponseBody
	@PostMapping("/login/loginProcess")
	public Mono<ResponseEntity<LoginResponseVo>> loginProcess(@RequestBody LoginRequestVo login
			, ServerHttpResponse response, WebSession session){

		// 로그인 응답객체
		LoginResponseVo responseVo = new LoginResponseVo();
		
		// 관리자 아이디
		String adminId = AES256Util.decode(login.getAdminId(), aes256.getPrivateKey());
		// 관리자 패스워드
		String password = AES256Util.decode(login.getPassword(), aes256.getPrivateKey());
		
		// 로그인 체크
		return loginService.loginProcess(adminId, password)
			// 관리자 권한 조회
			.zipWith(authService.selectAdminIdAuthList(adminId).collectList())
			// 세션 설정
			.flatMap(tuple -> {
				
				// 세션시작
				session.start();
				// 세션 시간설정 
				session.setMaxIdleTime(Duration.ofHours(3));
				// 세션 유저정보 저장
				session.getAttributes().put("user", tuple.getT1().createPublicAdminVo());
				// 세션 유저권한 저장
				session.getAttributes().put("auth", tuple.getT2().stream().map(auth -> auth.getMenuNo()).collect(Collectors.toList()));
				
				// 토큰 생성
				String newCsrfToken = CommonUtil.getRandomString(20);
				// CSRF 발행
				session.getAttributes().put("CSRF_TOKEN", newCsrfToken);
				response.addCookie(ResponseCookie.from("CSRF_TOKEN", newCsrfToken).maxAge(Duration.ofMinutes(30)).build());
				
				// 응답코드 메시지 세팅
				responseVo.setResultCode(ResponseConstant.SUCCESS.toString());
				responseVo.setResultMessage(ResponseConstant.SUCCESS.name());
				
				// 로그인 처리 응답
				return Mono.defer(() -> Mono.just(ResponseEntity.ok().body(responseVo)));
			}).onErrorResume(LoginException.class, error -> {
				
				error.printStackTrace();
				
				// 응답코드 메시지 세팅
				responseVo.setResultCode(error.getReason().name());
				responseVo.setResultMessage(ResponseConstant.valueOf(error.getReason().name()).getMessage());
				
				// 응답
				return Mono.defer(() -> Mono.just(ResponseEntity.ok().body(responseVo)));
			}).onErrorResume(error -> {
				
				error.printStackTrace();
				
				// 응답코드 메시지 세팅
				responseVo.setResultCode(ResponseConstant.INTERNAL_SERVER_ERROR.toString());
				responseVo.setResultMessage(ResponseConstant.INTERNAL_SERVER_ERROR.name());
				
				// 시스템 오류
				return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseVo));
			});
	}
	
	/**
	 * 로그인 처리
	 * @param login
	 * @param response
	 * @param session
	 * @return
	 */
	@ResponseBody
	@PostMapping("/old/login/loginProcess")
	public Mono<ResponseEntity<LoginResponseVo>> old_loginProcess(@RequestBody LoginRequestVo login
			, ServerHttpResponse response, WebSession session){
		
		// 관리자 아이디
		login.setAdminId(AES256Util.decode(login.getAdminId(), aes256.getPrivateKey()));
		// 관리자 패스워드
		login.setPassword(AES256Util.decode(login.getPassword(), aes256.getPrivateKey()));
		
		return loginService.old_loginProcess(login)
			
			.flatMap(responseVo -> {
				
				// 세션정보 등록
				if(responseVo.isLogin()) {
					// 세션시작
					session.start();
					// 세션 시간설정 
					session.setMaxIdleTime(Duration.ofHours(3));
					// 세션 유저정보 저장
					session.getAttributes().put("user", responseVo.getData());
					
					// 토큰 생성
					String newCsrfToken = CommonUtil.getRandomString(20);
					// CSRF 발행
					session.getAttributes().put("CSRF_TOKEN", newCsrfToken);
					response.addCookie(ResponseCookie.from("CSRF_TOKEN", newCsrfToken).maxAge(Duration.ofMinutes(30)).build());
				}
				
				// 응답코드 메시지 세팅
				responseVo.setResultCode(ResponseConstant.SUCCESS.toString());
				responseVo.setResultMessage(ResponseConstant.SUCCESS.name());
				
				// 로그인 처리 응답
				return Mono.defer(() -> Mono.just(ResponseEntity.ok().body(responseVo)));
			
			}).onErrorResume(LoginException.class, error -> {
				
				LoginResponseVo responseVo = new LoginResponseVo();
				
				// 응답코드 메시지 세팅
				responseVo.setResultCode(error.getReason().name());
				responseVo.setResultMessage(ResponseConstant.valueOf(error.getReason().name()).getMessage());
				
				// 응답
				return Mono.defer(() -> Mono.just(ResponseEntity.ok().body(responseVo)));
			}).onErrorResume(error -> {
				
				LoginResponseVo responseVo = new LoginResponseVo();
				
				// 응답코드 메시지 세팅
				responseVo.setResultCode(ResponseConstant.INTERNAL_SERVER_ERROR.toString());
				responseVo.setResultMessage(ResponseConstant.INTERNAL_SERVER_ERROR.name());
				
				// 시스템 오류
				return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseVo));
			});
	}
	
}
