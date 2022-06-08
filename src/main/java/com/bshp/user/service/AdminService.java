package com.bshp.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bshp.user.repository.AdminRepository;

import reactor.core.publisher.Mono;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	/**
	 * 관리자 여부 조회
	 * @param adminId
	 * @return
	 */
	public Mono<Boolean> isAdmin(String adminId){
		// 회원가입여부 조회
		return adminRepository.isAdmin(adminId).flatMap(map -> {
			int count = Integer.parseInt(map.get("count").toString());
			if(count > 0) {
				return  Mono.defer(() -> Mono.just(true));
			}else {
				return  Mono.defer(() -> Mono.just(false));
			}
		});
	}	
}
