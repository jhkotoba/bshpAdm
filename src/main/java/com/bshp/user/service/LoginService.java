package com.bshp.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bshp.common.util.AES256Util;
import com.bshp.user.repository.LoginRepository;
import com.bshp.user.vo.LoginRequestVo;
import com.bshp.user.vo.UserVo;

import reactor.core.publisher.Mono;

@Service
public class LoginService {
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Value("${aes256.private-key}")
	private String privateKey;
	
	/**
	 * 로그인 처리
	 * @param login
	 * @return
	 */
	public Mono<UserVo> loginProcess(LoginRequestVo login){
		
		String userId = AES256Util.decode(login.getUserId(), privateKey); 
		String password = AES256Util.decode(login.getPassword(), privateKey);
		
//		loginRepository.getLoginUser(userId).map(user -> {			
//			BCrypt//			
//			password   user.getPassword()//			
//			boolean userCheck = BCrypt.checkpw(param.getString("passwd"), mainMapper.selectUserPasswd(param));//			
//			return user;
//		});
		
		return Mono.empty();
	}
}
