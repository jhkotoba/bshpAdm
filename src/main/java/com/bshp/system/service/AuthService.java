package com.bshp.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bshp.system.repository.AuthRepository;
import com.bshp.system.vo.AuthVo;

import reactor.core.publisher.Flux;

@Service
public class AuthService {
	
	@Autowired
	private AuthRepository authRepository;
	
	public Flux<AuthVo> selectAdminIdAuthList(String adminId) {
		return authRepository.selectAdminIdAuthList(adminId);
	}

}
