package com.bshp.login.service;

import org.springframework.stereotype.Service;

import com.bshp.login.repository.LoginRepository;
import com.bshp.login.vo.LoginRequestVo;

import reactor.core.publisher.Mono;

@Service
public class LoginService {
	
	private LoginRepository loginRepository;

	public Mono<String> loginProcess(LoginRequestVo user){
		
		UserVo userVo = loginRepository.getUser(user.getUserId());
		return Mono.empty();		
		
	}
}
