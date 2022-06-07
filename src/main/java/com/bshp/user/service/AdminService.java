package com.bshp.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bshp.common.property.AES256Properties;
import com.bshp.common.util.AES256Util;
import com.bshp.user.repository.AdminRepository;
import com.bshp.user.vo.AdminRequestVo;

import reactor.core.publisher.Mono;

@Service
public class AdminService {
	
	@Autowired
	private AES256Properties aes256;
	
	@Autowired
	private AdminRepository adminRepository;
	
	/**
	 * 관리자 로그인, 회원가입 복호화 
	 * @param join
	 * @return
	 */
	public void adminDecode(AdminRequestVo join){
		
		// 관리자 아이디
		String adminId = AES256Util.decode(join.getAdminId(), aes256.getPrivateKey());
		// 관리자 패스워드
		String password = AES256Util.decode(join.getPassword(), aes256.getPrivateKey());
		
		// 복호화 데이터 세팅
		join.setAdminId(adminId);
		join.setPassword(password);
	}
	
	/**
	 * 
	 * @param adminId
	 * @return
	 */
	public Mono<Boolean> isAdmin(String adminId){
		// 회원가입여부 조회
		return adminRepository.isAdmin(adminId).flatMap(map -> {
			int count = Integer.parseInt(map.get("count").toString());
			if(count > 0) {
				return  Mono.defer(() -> Mono.just(true));
			}else {
				return  Mono.defer(() -> Mono.just(false));
			}
		});
	}	
}
