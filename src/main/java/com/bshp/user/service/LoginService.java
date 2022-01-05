package com.bshp.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bshp.common.property.AES256Properties;
import com.bshp.common.util.AES256Util;
import com.bshp.user.repository.LoginRepository;
import com.bshp.user.vo.LoginRequestVo;
import com.bshp.user.vo.PublicUserVo;

import reactor.core.publisher.Mono;

@Service
public class LoginService {
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private AES256Properties aes256;
	
	/**
	 * 로그인 처리
	 * @param login
	 * @return
	 */
	public Mono<PublicUserVo> loginProcess(LoginRequestVo login){
		
		String userId = AES256Util.decode(login.getUserId(), aes256.getPrivateKey()); 
		String password = AES256Util.decode(login.getPassword(), aes256.getPrivateKey());
		
		// 회원정보 조회
		return loginRepository.getLoginUser(userId).flatMap(user -> {
						
			// 비밀번호 체크
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
			PublicUserVo pUser = user.createUserVo(encoder.matches(password, user.getPassword()));
			return Mono.defer(() -> Mono.just(pUser));
		});
		
	}
}
