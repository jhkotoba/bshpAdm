package com.bshp.user.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bshp.common.property.AES256Properties;
import com.bshp.common.util.AES256Util;
import com.bshp.user.exception.LoginException;
import com.bshp.user.repository.LoginRepository;
import com.bshp.user.vo.LoginRequestVo;
import com.bshp.user.vo.LoginResponseVo;

import reactor.core.publisher.Mono;

@Service
public class LoginService {
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private AES256Properties aes256;
	
	/**
	 * 로그인 체크 및 처리
	 * @param login
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public Mono<LoginResponseVo> loginProcess(LoginRequestVo login){
		
		// 관리자 아이디
		String adminId = AES256Util.decode(login.getAdminId(), aes256.getPrivateKey());
		// 관리자 패스워드
		String password = AES256Util.decode(login.getPassword(), aes256.getPrivateKey());
		
		// 로그인 결과 객체
		LoginResponseVo response = new LoginResponseVo();;
		
		// 회원정보 조회
		return loginRepository.getLoginUser(adminId).flatMap(user -> {
			
			// 인코더 생성
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
			
			// 로그인 성공
			if(encoder.matches(password, user.getPassword())) {
				response.setPublicAdminVo(user, true);
				return  Mono.defer(() -> Mono.just(response));
			// 패스워드가 일치하지 않을 경우
			}else {
				return Mono.error(new LoginException(LoginException.reason.PASSWORD_DIFFERENT));
			}
		}).onErrorResume(LoginException.class, error -> Mono.error(error));
	}
}
