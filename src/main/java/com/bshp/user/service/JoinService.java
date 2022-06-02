package com.bshp.user.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bshp.user.repository.JoinRepository;
import com.bshp.user.vo.AdminRequestVo;

import reactor.core.publisher.Mono;

@Service
public class JoinService {
	
	@Autowired
	private JoinRepository joinRepository;
	
	/**
	 * 로그인 체크 및 처리
	 * @param login
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public Mono<Integer> joinRequest(AdminRequestVo join){
		
		// 암호 단반향 암호화 데이터 세팅
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
		String encPassword = encoder.encode(join.getPassword());
		join.setPassword(encPassword);
		
		return joinRepository.insertAdmin(join);
	}
	
}
