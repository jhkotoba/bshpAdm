package com.bshp.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.bshp.common.util.ValidationUtil;
import com.bshp.common.vo.ResponseVo;
import com.bshp.user.exception.JoinException;
import com.bshp.user.service.AdminService;
import com.bshp.user.service.JoinService;
import com.bshp.user.vo.AdminRequestVo;

import reactor.core.publisher.Mono;

@Controller
public class JoinController {
	
	@Autowired
	private JoinService joinService;	
	
	@Autowired
	private AdminService adminService;
	
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
	 * 가입신청 처리
	 * @param login
	 * @param response
	 * @param session
	 * @return
	 */
	@ResponseBody
	@PostMapping("/join/joinRequest")
	public Mono<ResponseEntity<ResponseVo<Void>>> joinRequest(@RequestBody AdminRequestVo join
			, ServerHttpResponse response){
		
		// 응답할 객체
		ResponseVo<Void> responseVo = new ResponseVo<Void>();
		
		// 아이디, 암호 복호화
		adminService.adminDecode(join);
		
		// 아이디 체크
		return adminService.isAdmin(join.getAdminId())
			// 등록하려는 아이디가 존재하는지 확인
			.flatMap(isAdm -> {
				if(isAdm) {
					// 등록하려는 아이디가 존재함
					return Mono.error(new JoinException(JoinException.reason.ID_ALREADY_EXIST));
					
				}else if(ValidationUtil.isEmpty(join.getPassword()) == false || ValidationUtil.isPassword(join.getPassword()) == false) {
					// 비밀번호값이 비어있거나 정상적이지 않음
					return Mono.error(new JoinException(JoinException.reason.INCORRECT_PASSWORD));
				
				}else if(ValidationUtil.isEmpty(join.getPhone()) == false || ValidationUtil.isPhone(join.getPhone()) == false) {
					// 전화번호 값이 비어있거나 정상적이지 않음 
					return Mono.error(new JoinException(JoinException.reason.INCORRECT_PHONE));
					
				}else if(ValidationUtil.isEmpty(join.getEmail()) == false || ValidationUtil.isEmail(join.getEmail()) == false) {
					// 이메일 값이 비어있거나 정상적이지 않음
					return Mono.error(new JoinException(JoinException.reason.INCORRECT_EMAIL));
					
				}else {
					// 등록 처리
					return joinService.joinRequest(join);
				}
			}).flatMap(rowUptCnt -> {
				
				if(rowUptCnt > 0) {
					// 응답코드 메시지 세팅
					responseVo.setResultCode(ResponseConstant.SUCCESS.toString());
					responseVo.setResultMessage(ResponseConstant.JOIN_SUCCESS.getMessage());
					return Mono.defer(() -> Mono.just(ResponseEntity.ok().body(responseVo)));					
				}else {
					// 등록실패
					return Mono.error(new JoinException(JoinException.reason.INSERT_FAIL));
				}
				
			// 정의된 예외 처리
			}).onErrorResume(JoinException.class, error -> {
				
				// 응답코드 메시지 세팅
				responseVo.setResultCode(error.getReason().name());
				responseVo.setResultMessage(ResponseConstant.valueOf(error.getReason().name()).getMessage());
				
				// 응답
				return Mono.defer(() -> Mono.just(ResponseEntity.ok().body(responseVo)));
				
			// 오류처리
			}).onErrorResume(error -> {
				
				// 응답코드 메시지 세팅
				responseVo.setResultCode(ResponseConstant.INTERNAL_SERVER_ERROR.toString());
				responseVo.setResultMessage(ResponseConstant.INTERNAL_SERVER_ERROR.getMessage());				
				
				// 시스템 오류
				return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseVo));
			});
	}
}
