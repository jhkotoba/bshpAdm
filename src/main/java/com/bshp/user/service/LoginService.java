package com.bshp.user.service;

import org.springframework.stereotype.Service;

import com.bshp.user.repository.LoginRepository;
import com.bshp.user.vo.LoginRequestVo;
import com.bshp.user.vo.UserVo;

import reactor.core.publisher.Mono;

@Service
public class LoginService {
	
	private LoginRepository loginRepository;

	public Mono<UserVo> loginProcess(LoginRequestVo user){
		
		return loginRepository.getUser(user.getUserId());
	}
}
