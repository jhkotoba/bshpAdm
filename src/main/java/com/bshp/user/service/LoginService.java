package com.bshp.user.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
	 * @throws NoSuchAlgorithmException 
	 */
	public Mono<PublicUserVo> loginProcess(LoginRequestVo login) throws Exception{
		
		String userId = AES256Util.decode(login.getUserId(), aes256.getPrivateKey()); 
		String password = AES256Util.decode(login.getPassword(), aes256.getPrivateKey());
		
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		
		// 회원정보 조회
		return loginRepository.getLoginUser(userId).flatMap(user -> {
			
			//md.update(user.getSalt().getBytes());
			md.update("iB2vnABCcbJVeC786rClTw==".getBytes());
			md.update(password.getBytes());
			String reqPassword = String.format("%0128x", new BigInteger(1, md.digest()));
						
			// 비밀번호 체크
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
			PublicUserVo pUser = user.createUserVo(encoder.matches(reqPassword, user.getPassword()));
			return Mono.defer(() -> Mono.just(pUser));
		});
		
	}
}
