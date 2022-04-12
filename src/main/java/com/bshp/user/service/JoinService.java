package com.bshp.user.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bshp.common.property.AES256Properties;
import com.bshp.common.util.AES256Util;
import com.bshp.user.repository.JoinRepository;
import com.bshp.user.vo.JoinRequestVo;

import reactor.core.publisher.Mono;

@Service
public class JoinService {
	
	@Autowired
	private JoinRepository joinRepository;
	
	@Autowired
	private AES256Properties aes256;
	
	/**
	 * 로그인 체크 및 처리
	 * @param login
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public Mono<Integer> joinRequest(JoinRequestVo join){
		
		// 관리자 아이디
		String adminId = AES256Util.decode(join.getAdminId(), aes256.getPrivateKey());
		// 관리자 패스워드
		String password = AES256Util.decode(join.getPassword(), aes256.getPrivateKey());
		
		// 인코더 생성
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
		
		String encPassword = encoder.encode(password);
		
		join.setAdminId(adminId);
		join.setPassword(encPassword);
		
		return joinRepository.insertAdmin(join);
	}
	
}
