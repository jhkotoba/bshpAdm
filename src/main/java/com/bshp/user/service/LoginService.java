package com.bshp.user.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bshp.common.property.AES256Properties;
import com.bshp.common.util.AES256Util;
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
	public Mono<LoginResponseVo> loginProcess(LoginRequestVo login) throws Exception{
		
		// 사용자 아이디
		String userId = AES256Util.decode(login.getUserId(), aes256.getPrivateKey());
		// 사용자 패스워드
		String password = AES256Util.decode(login.getPassword(), aes256.getPrivateKey());
		
		// 로그인 결과 객체
		LoginResponseVo response = new LoginResponseVo();;
		
		// 회원정보 조회
		return loginRepository.getLoginUser(userId).flatMap(user -> {
			
			// 인코더 생성
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
			
			// 조회되는 아이디가 없을 경우
			if(user == null) {
				response.setPublicUserVo(user, false);
			// 로그인 성공
			}else if(encoder.matches(password, user.getPassword())) {
				response.setPublicUserVo(user, true);
			// 패스워드가 일치하지 않을 경우
			}else {
				response.setPublicUserVo(user, false);				
			}
			
			// 결과 반환
			return Mono.defer(() -> Mono.just(response));
		});
		
	}
}
