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

import com.bshp.common.vo.ResponseVo;
import com.bshp.user.repository.AdminRepository;
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
	public Mono<ResponseEntity<Integer>> joinRequest(@RequestBody AdminRequestVo join
			, ServerHttpResponse response){
		
		// 아이디, 암호 복호화
		adminService.adminDecode(join);
		
		return adminService.isAdmin(join.getAdminId())
			.flatMap(isAdm -> {
				if(isAdm) {
					return Mono.error(new Exception("ADMIN ERR"));
				}else {
					return joinService.joinRequest(join);
				}
			}).flatMap(f -> {
				
				return Mono.empty();
			});
		
		
//		flatMap(f -> {
//			ResponseVo<Integer> res = new ResponseVo<Integer>();
//			res.setData(f);
//			return Mono.defer(() -> Mono.just(ResponseEntity.ok().body(res)));
//		}).onErrorResume(error -> {
//			
//			// 시스템 오류
//			return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
//		});
		
		
		
		
		// 아이디, 암호 복호화
//		Mono.just(join).map(admin -> {
//			adminService.adminDecode(admin);
//			return adminService.isAdmin(admin.getAdminId());
//		}).flatMap(isAdmin -> {
//			if(isAdmin) {
//				
//			}
//			
//		});
			
		
		
		//adminService.isAdmin(join.getAdminId())
			
			
		
		
//		Mono<Integer> result = adminService.adminDecode(join)
//			.map(admin -> adminService.isAdmin(admin.getAdminId())
//				.flatMap(isAdmin -> {
//					if(isAdmin) {
//						return Mono.error(new Exception("NOT ADMIN"));
//					}else {
//						return joinService.joinRequest(join);
//					}
//				})
//			).flatMap(map -> {
//								
//				
//			});
		
		
		
		
		
//			.flatMap(isAdmin ->  {
//				
//				
//				
//				if(isAdmin) {
//					return Mono.error(new Exception("NOT ADMIN"));
//				}else {
//					return joinService.joinRequest(join);
//					
//				}
//			});
		
//		return Mono.empty();
			


			
		
		
//		return joinService.joinRequest(join).flatMap(f -> {
//			
//			return Mono.empty();
//			
//		});
		
		
//		return joinService.joinRequest(join).map(f -> {
//			ResponseVo<Integer> res = new ResponseVo<Integer>();
//			res.setData(f);
//			return Mono.defer(() -> Mono.just(ResponseEntity.ok().body(res)));
//		}).onErrorResume(error -> {
//			
//			// 시스템 오류
//			return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
//		});
	}
}
